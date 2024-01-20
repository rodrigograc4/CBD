function countPhonesByPrefix(prefix) {
    return db.phones.find({ "components.prefix": prefix }).count();
  }
  
  var prefixes = [21, 22, 231, 232, 233, 234];
  for (var i = 0; i < prefixes.length; i++) {
    var prefix = prefixes[i];
    var count = countPhonesByPrefix(prefix);
    print("Prefix " + prefix + ": " + count + " phones");
  }
  