const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

exports.updateGroupBalance = functions.firestore.document("/groups/{groupId}")
    .onUpdate(async (change, context) => {
      const groupId = context.params.groupId;
      const unprocessedPaymentsSnapshot = await admin.firestore().collection('unprocessedPayments')
        .where(admin.firestore.FieldPath.documentId(), '==', groupId).get();

      if (unprocessedPaymentsSnapshot.empty) return;

      const groupData = change.after.data();
      const currentPayments = groupData.payments || [];
      const unprocessedPaymentsIds = [];
      unprocessedPaymentsSnapshot.forEach(doc => {
        unprocessedPaymentsIds.push(...doc.data().paymentsIds);
      });

      const unprocessedPayments = currentPayments
        .filter(payment => { return unprocessedPaymentsIds.includes(payment.id); });

      if (unprocessedPayments.length == 0) return;

      const currentBalance = groupData.balance || {};

      unprocessedPayments.forEach(payment => {
        functions.logger.log('payment', payment);
        const total = Number(payment.value || 0);
        const shared = Number(total / (payment.paidTo.length || 1));

        payment.paidTo.forEach(member => {
          const paidToKey = member.uid;
          const currentPaidToKeyBalance = Number(currentBalance[paidToKey] || 0);
          currentBalance[paidToKey] = String(currentPaidToKeyBalance + shared);
        });
        const paidByKey = payment.paidBy.uid;
        const currentPaidByKeyBalance = Number(currentBalance[paidByKey] || 0);
        currentBalance[paidByKey] = String(currentPaidByKeyBalance - total);
      });

      const groupDoc = admin.firestore().collection("groups").doc(groupId);
      const unprocessedPaymentsDoc = admin.firestore().collection("unprocessedPayments").doc(groupId);

      const batch = admin.firestore().batch();
      batch.update(groupDoc, {balance: currentBalance});
      batch.delete(unprocessedPaymentsDoc);
      return batch.commit();
    });