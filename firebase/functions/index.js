const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

exports.processPayment = functions
    .firestore
    .document("/unprocessedPayments/{paymentId}")
    .onCreate(async (paymentSnapshot, context) => {      
      const paymentData = paymentSnapshot.data();  
      const groupRef = admin.firestore().collection("groups").doc(paymentData.groupId);
      const groupDoc = await groupRef.get();    
      const groupData = groupDoc.data();

      const groupCurrentBalance = groupData.balance || {};
      const paymentTotalValue = Number(paymentData.value || 0);
      const paymentTotalWeight = Object.values(paymentData.paidTo).reduce((sum, weight) => sum + weight, 0);

      Object.entries(paymentData.paidTo).forEach(([memberId, memberWeight]) => {
        const paymentSharedValue = paymentTotalValue * (memberWeight / paymentTotalWeight);
        const adjustment = memberId === paymentData.paidBy ? (paymentTotalValue - paymentSharedValue) * -1 : paymentSharedValue;
        const currentPaidToKeyBalance = Number(groupCurrentBalance[memberId] || 0);

        groupCurrentBalance[memberId] = String(currentPaidToKeyBalance + adjustment);
      });

      if (!paymentData.paidTo.hasOwnProperty(paymentData.paidBy)) {
        const currentBalance = Number(groupCurrentBalance[paymentData.paidBy] || 0);
        groupCurrentBalance[paymentData.paidBy] = String(currentBalance - paymentTotalValue);
      }

      paymentSnapshot.ref.delete();
      return groupRef.update({balance: groupCurrentBalance});
    });