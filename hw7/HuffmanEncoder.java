import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> ret = new HashMap<>();
        for (char c : inputSymbols) {
            Integer freq = ret.get(c);
            if (freq == null) {
                ret.put(c, 1);
            } else {
                ret.put(c, freq + 1);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        char[] inputChars = FileUtils.readFile(args[0]);
        String hufFile = args[0] + ".huf";
        Map<Character, Integer> freqTable = buildFrequencyTable(inputChars);
        ObjectWriter ow = new ObjectWriter(hufFile);
        BinaryTrie bt = new BinaryTrie(freqTable);
        ow.writeObject(bt);

        Map<Character, BitSequence> lookupTable = bt.buildLookupTable();
        List<BitSequence> bs = new ArrayList<>();
        for (char c : inputChars) {
            bs.add(lookupTable.get(c));
        }

        BitSequence hugeBitSequence = BitSequence.assemble(bs);
        ow.writeObject(hugeBitSequence);
    }
}
