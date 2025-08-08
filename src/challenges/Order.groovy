package challenges

class Order {
  static void main(String[] args) {

    def message = "1c1c01041963730f31352a3a386e24356b3d32392b6f6b0d323c22243f63731a0d0c302d3b2b1a292a3a38282c2f222d2a112d282c31202d2d2e24352e60"
    List<Byte> messageAsBytes = message.decodeHex()

    def knownHeader = 'ORDER:'.tokenize('')
    List<Integer> probableKey = []
    for (Integer i = 0; i < knownHeader.size(); i++) {
      for (int j = 0; j < 256; j++) {
        if ((messageAsBytes[i] ^ j) == knownHeader[i]) {
          probableKey.add(j)
        }
      }
    }

    List<String> decryptedMessage = []
    def messageAsBytesLength = messageAsBytes.size()
    for (int i = 0; i < messageAsBytesLength; i += probableKey.size()) {
      def subList
      if (messageAsBytes.subList(i, messageAsBytesLength - 1).size() < probableKey.size()) {
        subList = messageAsBytes.subList(i, messageAsBytesLength - 1)
      } else {
        subList = messageAsBytes.subList(i, i + probableKey.size())
      }

      List<List<Integer>> zippedLists = [probableKey, subList].transpose()
      def entriesXored = zippedLists.collect {
        def list = it as List
        return (list[0] ^ list[1]) as Character
      }
      decryptedMessage.add(entriesXored.join(''))
    }
    println(decryptedMessage.join(''))
  }
}
