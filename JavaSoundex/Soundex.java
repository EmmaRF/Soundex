/**
 * GitHub: https://github.com/EmmaRF/Soundex/blob/master/JavaSoundex/Soundex.java
 * @author Emma R. Fronberg
 */
public class Soundex {
    // names that will be tested
    static String emmaName = "Emma Rose Fronberg";
    static String enterpriseScience = "Spock";
    static String enterpriseCaptain = "James Tiberius Kirk";
    static String hWString = "Hannshs bhbcwc";
    
    /**
     *  Converts the given string into the soundex form of that string.
     * 
     *  @param  str  a string that will be converted into soundex form
     *  @return      the soundex form of the given string
     */
    public static String soundex(String str){
        if(str.length() == 0){                                   // Checks if the string is "" or not
            return str;
        }
        
        String numStr = str; // Converted str into relevant numbers, including first letter
        String answer = "";  // The str in Soundex form                

        // Replace all relevant letters with numbers
        numStr = numStr.replaceAll("[BbFfPpVv]", "1");               // Step 2
        numStr = numStr.replaceAll("[CcGgJjKkQqSsXxZz]", "2");       // Step 2
        numStr = numStr.replaceAll("[DdTt]", "3");                   // Step 2
        numStr = numStr.replaceAll("[Ll]", "4");                     // Step 2
        numStr = numStr.replaceAll("[MmNn]", "5");                   // Step 2
        numStr = numStr.replaceAll("[Rr]", "6");                     // Step 2

        // Step 3
        for(int i = 1; i < numStr.length(); i++){
            // Delete excessive repeating digits, including first letter repeats
            if(numStr.charAt(i - 1) == numStr.charAt(i)){
                if(i + 1 < numStr.length()){ 
                    numStr = numStr.substring(0, i) + numStr.substring(i + 1);
                } else {
                    numStr = numStr.substring(0, i);
                }
            }
            // Deletes repeating digits with H or W inbetween
            if(i + 1 < numStr.length()){
                switch(numStr.charAt(i)){
                case 'H':
                case 'h':
                case 'W':
                case 'w':
                    if(numStr.charAt(i - 1) == numStr.charAt(i + 1)){
                        numStr = numStr.substring(0, i) + numStr.substring(i + 2);
                    }
                    break;
                default:
                    // Do Nothing.
                }
            }
        }
        
        // Regular Expressions
        String ignore = "[AaEeIiOoUuYyHhWw]";
        
        String[] splitNumStr = numStr.split(" ");
        String[] splitStr = str.split(" ");
        for(int i = 0; i < splitStr.length; i++){
            
            // Deletes vowels, Hs, and Ws
            String restOfNumSplit = splitNumStr[i].substring(1);     // Step 1
            restOfNumSplit = restOfNumSplit.replaceAll(ignore, ""); 
            
            // Replace first letter
            splitNumStr[i] = splitStr[i].charAt(0) + restOfNumSplit; // Step 1

            // Removes extra digits and add 0s when needed
            if(splitNumStr[i].length() > 4){                         // Step 4
                splitNumStr[i] = splitNumStr[i].substring(0, 4);
            } else{                                                  // length is <=
                while(splitNumStr[i].length() < 4){
                    splitNumStr[i] = splitNumStr[i] + "0";           // Step 4
                }
            }
            
            answer = answer + splitNumStr[i] + " ";                  // Turns the array into a single string again
        }
        return answer.substring(0, answer.length() - 1);             // Remove extra space at the end of the string
    }
    
    // Writing specific names in a nice string.
    private static String niceTestString(String name){
        return "Original name: " + name + "\nSoundex form:  " + soundex(name) + "\n";
    }
    
    
    /**
    * @param args 
    */
    public static void main(String[] args) {
        // // Testing undefined input won't work, 
        // System.out.println(niceTestString()); // Throws Error
    
        // Testing empty string
        System.out.println(niceTestString(""));
        
        // Testing Spock's name (testing a single word, which would end in the same digits)
        System.out.println(niceTestString(enterpriseScience));
        
        // Testing Emma's name (testing multiple names works) 
        System.out.println(niceTestString(emmaName));
        
        // Testing Captain Kirk's name (checking that the given example turns out the same)
        System.out.println(niceTestString(enterpriseCaptain));
        
        // Testing H and W in middle name (with the same values), it isn't meant to be a name
        System.out.println(niceTestString("Hannshs bhbcwc"));
    }   
}