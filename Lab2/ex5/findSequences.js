function findDigitSequencesInPhones() {
    var sequences = {};
    var phones = db.phones.find();
    
    phones.forEach(function (phone) {
      var phoneNumber = phone.components.number.toString();
      var currentSequence = phoneNumber[0];
      
      for (var i = 1; i < phoneNumber.length; i++) {
        if (parseInt(phoneNumber[i]) === parseInt(phoneNumber[i - 1]) + 1) {
          currentSequence += phoneNumber[i];
        } else {
          if (currentSequence.length >= 2) {
            sequences[currentSequence] = (sequences[currentSequence] || 0) + 1;
          }
          currentSequence = phoneNumber[i];
        }
      }
    
      if (currentSequence.length >= 2) {
        sequences[currentSequence] = (sequences[currentSequence] || 0) + 1;
      }
    });
    
    return sequences;
  }
  
  var sequenciasEncontradas = findDigitSequencesInPhones();
  for (var sequence in sequenciasEncontradas) {
    print("Sequência: " + sequence + ", Quantidade de vezes: " + sequenciasEncontradas[sequence]);
  }
  