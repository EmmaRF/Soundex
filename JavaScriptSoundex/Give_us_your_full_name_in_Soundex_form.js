// GitHub: https://github.com/EmmaRF/Soundex/blob/master/JavaScriptSoundex/Give_us_your_full_name_in_Soundex_form.js
var emmaName = "Emma Rose Fronberg";
var enterpriseCaptain = "James Tiberius Kirk";
var enterpriseScience = "Spock";

function soundex(str) { 
    
    if(!str || str.length === 0){ // Checks if the string is empty or undefined
        return str;
    }
    
    let numStr = ""; // Converted str into relevant numbers, including first letter
    let result = ""; // The str in Soundex form
    
    // Regular Expressions
    let start = /(\b\w)/gi;                    // Step 1
    let ignore = /(?!\b\w)[aeiouyhw]/gi;       // Step 1
    let bfpv = /[bfpv]/gi;                     // Step 2
    let cgjkqsxz = /[cgjkqsxz]/gi;             // Step 2
    let dt = /[dt]/gi;                         // Step 2
    let l = /[l]/gi;                           // Step 2
    let mn = /[mn]/gi;                         // Step 2
    let r = /[r]/gi;                           // Step 2
    let repeat = /(\d)\1{1,}/gi;               // Step 3
    let separatedByHOrW = /(\d)(h|w)\1{1,}/gi; // Step 3 
    
    // Replace all relevant letters with numbers
    numStr = str.replace(bfpv, "1");           // Step 2
    numStr = numStr.replace(cgjkqsxz, "2");    // Step 2
    numStr = numStr.replace(dt, "3");          // Step 2
    numStr = numStr.replace(l, "4");           // Step 2
    numStr = numStr.replace(mn, "5");          // Step 2
    numStr = numStr.replace(r, "6");           // Step 2
    
    // Delete excessive repeating digits, including first letter repeats
    numStr = numStr.replace(repeat, function (x) {  // Step 3
        return x%10;                                // The remainder would be a single instance of that number
    });                                              
    
    let hWIndex = numStr.search(separatedByHOrW);   // Step 3, find the locations of same digits separated by Hs and Ws
    
    while(hWIndex > -1){                            // Step 3, while there exists two digits of the same number separated by an H or W, remove the h/w and the repeated digit
        numStr = numStr.slice(0, hWIndex + 1) + numStr.slice(hWIndex + 3);
        hWIndex = numStr.search(separatedByHOrW);
    }
    
    // Split into arrays
    var splitNumArray = numStr.split(" "); 
    var splitStrArray = str.split(" ");
    
    // Replace first letter
    for(var i = 0; i < splitStrArray.length; i++){ 
        splitNumArray[i] = splitNumArray[i].replace(start, splitStrArray[i].charAt(0)); // Step 1, make sure that the first letter is not a number
        splitNumArray[i] = splitNumArray[i].replace(ignore, "");                        // Step 1, remove any vowels, Hs, and Ws (excluding the first letter)
        
        if(splitNumArray[i].length < 4){                                                // Step 4, add extra 0s when needed
            while(splitNumArray[i].length < 4){
                splitNumArray[i] = splitNumArray[i] + "0";
            }
        } else {                                                                        // Step 4, remove extra digits from each word
            splitNumArray[i] = splitNumArray[i].slice(0,4);
        }
        result = result + splitNumArray[i] + " ";                                       // Turns the array into a single string again
    }
    return result.slice(0, result.length - 1);                                          // Removes extra space at the end.
}


// Writing specific names in a nice string.
function niceTestString(name){
    return "Original name: " + name + "\nSoundex form:  " + soundex(name) + "\n";
}

// Testing specific names
function tests(){
    // Testing undefined input
    console.log(niceTestString()); 
    
    // Testing empty string
    console.log(niceTestString(""));
    
    // Testing Spock's name (testing a single word, which would end in the same digits)
    console.log(niceTestString(enterpriseScience));
    
    // Testing Emma's name (testing multiple works) 
    console.log(niceTestString(emmaName));
    
    // Testing Captain Kirk's name (checking that the given example turns out the same)
    console.log(niceTestString(enterpriseCaptain));
    
    // Testing H and W in middle name (with the same values), it isn't meant to be a name
    console.log(niceTestString("Hannshs bhbcwc")); 
}
tests(); // Running tests