const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

exports.updateGroupBalance = functions.firestore.document("/groups/{groupId}")
    .onUpdate(async (change, context) => {
      const groupId = context.params.groupId;
      const groupData = change.after.data();
      const currentPayments = groupData.payments || [];
      const currentBalance = groupData.balance || {};

      processedPayments = currentPayments.map(payment => {
        if(payment.processed == false) {
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
            payment.processed = true;
        }
        return payment;
      })

      const groupDoc = admin.firestore().collection("groups").doc(groupId);
      groupDoc.update({balance: currentBalance, payments: processedPayments});
    });