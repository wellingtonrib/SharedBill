const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

exports.processPayment = functions.firestore.document("/unprocessedPayments/{paymentId}")
    .onCreate(async (paymentSnapshot, context) => {      
      const paymentData = paymentSnapshot.data();  
      const groupRef = admin.firestore().collection("groups").doc(paymentData.groupId);
      const groupDoc = await groupRef.get();    
      const groupData = groupDoc.data();

      const groupCurrentBalance = groupData.balance || {};
      const paymentTotalValue = Number(paymentData.value || 0);
      const paymentSharedValue = Number(paymentTotalValue / (paymentData.paidTo.length || 1));

      paymentData.paidTo.forEach(member => {        
        const currentPaidToKeyBalance = Number(groupCurrentBalance[member.uid] || 0);
        groupCurrentBalance[member.uid] = String(currentPaidToKeyBalance + paymentSharedValue);
      });      
      const currentPaidByKeyBalance = Number(groupCurrentBalance[paymentData.paidBy.uid] || 0);
      groupCurrentBalance[paymentData.paidBy.uid] = String(currentPaidByKeyBalance - paymentTotalValue);
              
      paymentSnapshot.ref.delete();
      return groupRef.update({balance: groupCurrentBalance});
    });