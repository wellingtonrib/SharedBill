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
        const paymentSharedValueFixed = Number(paymentSharedValue.toFixed(2));
        const adjustment = memberId === paymentData.paidBy ? (paymentTotalValue - paymentSharedValueFixed) * -1 : paymentSharedValueFixed;
        const currentBalance = Number(groupCurrentBalance[memberId] || 0);
        const updatedBalance = currentBalance + adjustment;
        const updatedBalanceFixed = Number(updatedBalance.toFixed(2));
        groupCurrentBalance[memberId] = String(updatedBalanceFixed);
      });

      if (!paymentData.paidTo.hasOwnProperty(paymentData.paidBy)) {
        const currentBalance = Number(groupCurrentBalance[paymentData.paidBy] || 0);
        const updatedBalance = currentBalance - paymentTotalValue;
        const updatedBalanceFixed = Number(updatedBalance);
        groupCurrentBalance[paymentData.paidBy] = String(updatedBalanceFixed);
      }

      paymentSnapshot.ref.delete();
      return groupRef.update({balance: groupCurrentBalance});
    });