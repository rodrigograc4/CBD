function findCapicuasInPhones() {
    var capicuas = {};
    var phones = db.phones.find();
  
    phones.forEach(function (phone) {
      var phoneNumber = phone.components.number;
      if (isCapicua(phoneNumber)) {
        if (capicuas[phoneNumber]) {
          capicuas[phoneNumber]++;
        } else {
          capicuas[phoneNumber] = 1;
        }
      }
    });
  
    return capicuas;
  }
  

  var capicuasEncontrados = findCapicuasInPhones();
  for (var number in capicuasEncontrados) {
    print("Número Capicua: " + number + ", Quantidade de vezes: " + capicuasEncontrados[number]);
  }
  
  function isCapicua(number) {
    var numberStr = number.toString();
  
    var startIndex = 0;
    var endIndex = numberStr.length - 1;
  
    while (startIndex < endIndex) {
      if (numberStr.charAt(startIndex) !== numberStr.charAt(endIndex)) {
        return false;
      }
  
      startIndex++;
      endIndex--;
    }
  
    return true;
  }
  