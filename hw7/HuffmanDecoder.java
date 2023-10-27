import java.util.ArrayList;

public class HuffmanDecoder {
    public static void main(String[] args) {
        String readFile = args[0];
        String writeFile = args[1];

        ObjectReader or = new ObjectReader(readFile);
        BinaryTrie bs = (BinaryTrie) or.readObject();
        BitSequence hugeSequence = (BitSequence) or.readObject();
        ArrayList<Character> symbols = new ArrayList<>();
        while (hugeSequence.length() != 0) {
            Match m = bs.longestPrefixMatch(hugeSequence);
            symbols.add(m.getSymbol());
            hugeSequence = hugeSequence.allButFirstNBits(m.getSequence().length());
        }
        char[] chars = new char[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            chars[i] = symbols.get(i);
        }
        FileUtils.writeCharArray(writeFile, chars);
    }
}
