// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 2 - Huffman Coding

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanSubmit implements Huffman {
	public static class Node {
		final private Character c;
		final private Integer freq;
		final private Node left;
		final private Node right;

		public Node(Character c, Integer freq, Node left, Node right) {
			this.c = c;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		public Boolean isLeaf() {
			return (this.left == null && this.right == null);
		}
	}

	public static void getCodes(String[] table, Node node, String prefix) {
		if (node.isLeaf()) {
			table[(int)node.c] = prefix;
			return;
		}
		getCodes(table, node.left, prefix + "0");
		getCodes(table, node.right, prefix + "1");
	}

	public static int[] getCharFreq(String inputString) {
		int[] freq = new int[256];
		BinaryIn in = new BinaryIn(inputString);
		while (!in.isEmpty()) {
			char c = in.readChar();
			freq[c]++;
		}
		return freq;
	}

	public static Node buildTree(int[] freq) {
		PriorityQueue<Node> pQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.freq));
		for (char i = 0; i < freq.length; i++) {
			if (freq[i] > 0) {
				pQueue.add(new Node(i, freq[i], null, null));
			}
		}

		while (pQueue.size() > 1) {
			Node left = pQueue.remove();
			Node right = pQueue.remove();
			Node parent = new Node(null, left.freq + right.freq, left, right);
			pQueue.add(parent);
		}

		return pQueue.remove();
	}

	// Feel free to add more methods and variables as required.
	/**
 *     Encodes the input file using Huffman Coding. Produces two files
 *
 *     @param inputFile The name of the input file to be encoded.
 *          Do not modify this file.
 *
 *     @param outputFile The name of the output file  (after encoding)
 *                This would be a binary file.
 *                If the file already exists, overwrite it.
 *
 *     @param freqFile  Stores the frequency of each byte
 *          This file is a text file
 *          where each row contains texual representation
 *          of each byte and the  number of occurence of this byte
 *          separated by ':'
 *          An example entry would look like:
 *          01100001:12345
 *          Which means
 *          the letter a (ascii code 097, binary representation 01100001)
 *          has occureed 12345. This file does not need to be sorted.
 *          If this file already exists, overwrite.
 *                     */
	@Override
	public void encode(String inputFile, String outputFile, String freqFile){
		int[] freq = getCharFreq(inputFile);
		Node root = buildTree(freq);
		String[] codes = new String[256];
		getCodes(codes, root, "");

		try (FileWriter fWriter = new FileWriter(freqFile)) {
			for (int i = 0; i < freq.length; i++) {
				fWriter.write(String.format("%08d", Integer.parseInt(Integer.toBinaryString(i))) + ":" + freq[i]);
				fWriter.write("\n");
			}
		} catch (IOException e) {}
		BinaryIn input = new BinaryIn(inputFile);
		BinaryOut output = new BinaryOut(outputFile);
		while (!input.isEmpty()) {
			char c = input.readChar();
			String code = codes[c];
			for (int i = 0; i < code.length(); i++) {
				if (code.charAt(i) == '1') {
					output.write(true);
				} else {
					output.write(false);
				}
			}
		}
		output.close();
	}
/**
 *     Decodes the input file (which is the output of encoding())
 *     using Huffman decoding.
 *
 *     @param inputFile The name of the input file to be decoded.
 *     Do not modify this file.
 *
 *     @param outputFile The name of the output file  (after decoding)
 *
 *     @param freqFile  freqFile produced after encoding.
 *     Do not modify this file.
 *                     */
	@Override
	public void decode(String inputFile, String outputFile, String freqFile){
		int[] freq = new int[256];
		try (BufferedReader bReader = new BufferedReader(new FileReader(freqFile))) {
			String line;
			while ((line = bReader.readLine()) != null) {
				String[] parts = line.split(":");
				int charCode = Integer.parseInt(parts[0], 2);
				int frequency = Integer.parseInt(parts[1]);
				freq[charCode] = frequency;
			}
    	} catch (IOException e) {}
		Node root = buildTree(freq);
		BinaryIn input = new BinaryIn(inputFile);
		BinaryOut output = new BinaryOut(outputFile);
		Node curr = root;
		while (!input.isEmpty()) {
			boolean bit = input.readBoolean();
			if (bit) {
				curr = curr.right;
			} else {
				curr = curr.left;
			}
			if (curr.isLeaf()) {
				output.write(curr.c);
				curr = root;
			}
		}
		output.close();
	}
	public static void main(String[] args) {
		Huffman huffman = new HuffmanSubmit();
		huffman.encode("ur.jpg", "ur.enc", "freq.txt");
		huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");
		huffman.encode("alice30.txt", "alice30.enc", "freq_alice30.txt");
		huffman.decode("alice30.enc", "alice30_dec.txt", "freq_alice30.txt");
		// After decoding, both ur.jpg and ur_dec.jpg should be the same.
		// On linux and mac, you can use `diff' command to check if they are the same.
	}

}
