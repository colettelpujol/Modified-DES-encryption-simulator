import java.util.Arrays;
import java.util.Scanner;

public class Encrypt {

  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter a ten bit binary string to encrypt"); 
    String input = scan.nextLine();
    int[] bitString = new int[input.length()];
    for (int i=0; i<bitString.length; i++){
      bitString[i] = input.charAt(i) - '0';
    }  
    System.out.println("Enter a ten bit key"); 
    String in = scan.nextLine();
    int[] key = new int[10];
    for (int i = 0; i < 10; i++){ 
      key[i] = in.charAt(i) - '0';  
    }
   
    System.out.println("Encrypting");
    twoRoundEncryptPrint(bitString, key);
    System.out.println("");
   
  }  
 
/**
 * XORs 2 arrays.
 * @param array1 one of the arrays to be XORed
 * @param array2 one of the arrays to be XORed
 * @return the 2 arrays XORed together
 */
  public static int[] XOR(int[] array1, int[] array2){
    int[] xor = new int[array1.length];
    for(int i=0; i<array1.length; i++){
      xor[i] = (array1[i] + array2[i])%2;
    }

    return xor;
  }
/**
 * Performs the function that is part of the Feistal System
 * @param right the right half of the 10 bit bitstring
 * @param key one of the 5 bit keys from the 10 bit encryption key
 * @return the 5 bit result of the function 
 */
  public static int[] function(int[] right, int[] key){
    int[] temp = XOR(right, key);
    int[] fnx = new int[right.length];
    fnx[0] = (temp[0] + temp[1])%2;
    fnx[1] = temp[1] * temp[2];
    fnx[2] = (temp[2] + temp[3])%2;
    fnx[3] = temp[3] * temp[4];
    fnx[4] = (temp[0] + temp[4])%2;
    return fnx;
  }  
 
  
/**
 * Prints an array.
 * @param array the array to be printed
 */
  public static void printArray(int[] array){
    for(int i = 0; i<array.length; i++){
      System.out.print(array[i]);
    }
  }

/**
 * Performs one round of the Feistal System.
 * @param string the bitstring to be encrypted
 * @param key the key that is being used to encrypt
 * @return the bitstring encrypted one time
 */
  public static int[] encrypt(int[] string, int[] halfKey){
    int[] bitStringR = new int[5];
    int[] bitStringL = new int[5];
    for (int i = 0; i < 5; i++){ //left half
      bitStringL[i] = string[i];  
    }

    for (int i = 5; i < 10; i++){ //right half
      bitStringR[i-5] = string[i];  
    }
    
    int[] Lnext = bitStringR;
    int[] Rnext = XOR(bitStringL, function(bitStringR, halfKey));
    int[] nextRound = new int[10];
    for(int i = 0; i<5; i++){
      nextRound[i] = Lnext[i];
    }
    
    for(int i = 5; i<10; i++){
      nextRound[i] = Rnext[i-5];
    }
    return nextRound;
  }
/**
 * This performs 2 rounds of the regular DES algorithm.
 * @param bitString the bitstring to be encrypted
 * @param key the key used to encrypt the bitstring
 * @return the encrypted bitstring
 */
  public static int[] twoRoundEncrypt(int[] bitString, int[] key){
    int[] key1 = new int[5];
    int[] key2 = new int[5];
    for (int i = 0; i < 5; i++){ //left half
      key1[i] = key[i];  
    }

    for (int i = 5; i < 10; i++){ //right half
      key2[i-5] = key[i];  
    }
    
    bitString = encrypt(bitString, key1);
    bitString = encrypt(bitString, key2);

    return bitString;
  }


/**
 * Prints the bitstring that has been encrypted by the regular DES algorithm.
 * @param bitString the bitstring to be encrypted
 * @param key the key used to encrypt the message
 */
  public static void twoRoundEncryptPrint(int[] bitString, int[] key){
    int[] key1 = new int[5];
    int[] key2 = new int[5];
    for (int i = 0; i < 5; i++){ //left half
      key1[i] = key[i];  
    }

    for (int i = 5; i < 10; i++){ //right half
      key2[i-5] = key[i];  
    }
    System.out.print("round " + 1 + " ");
    int round1 [] = encrypt(bitString, key1);
    printArray(round1);
    System.out.println("");
    System.out.print("round " + 2 + " ");
    printArray(encrypt(round1, key2));
 

  }


  

}
