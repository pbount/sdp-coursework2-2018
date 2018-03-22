# sdp-coursework2-2018
#####By Vasilios Davios, Pavlos Bountagkidis

##Objective
The problem is a programmatic implementation of Caesar (De)Cipher consisting of two parts each focused on a main function:

```encipher(s, n)``` and ```decipher(s)```


### ```encipher(s, n)```
This function receives a string and a number and produces another string with each character of the string moved by n steps of the english alphabet.

Example: ```encipher("a",1)``` would produce ```b```, ```encipher("x",3)``` would produce ```a```.

### ```decipher(s)```
This function deciphers a previously enciphered string, without however knowing the number of steps with which the string was enciphered. The number of steps must be deduced programmatically and then applied to the enciphered string to get the original text.

##Solution

### Approach to ```encipher(s, n)```
To encipher each character of the ```s``` string, the numeric representations from the ASCII table were used for the uppercase and lowercase representations of each alphabetical character. Since these characters are represented in order, (65-90 for Uppercase, 97-122 for lowercase) the ranges 65-90 and 97-122 were used directly to receive the enciphered character.

The formula used for enciphering a character was:

######Uppercase:
```(x + n - 65) % 26 + 65)``` where ```x``` = the numeric representation of the uppercase character, ```n``` = The number of steps the character is to be moved.

######Lowercase:
```(x + n - 97) % 26 + 97``` where ```x``` = the numeric representation of the lowercase character, ```n``` = The number of steps the character is to be moved.

### Approach to ```decipher(s)```
There is no absolute way to determine whether a word is valid from a mathematical perspective. A single enciphered word, is not sufficient to determine it's encipher step by any means, other than a direct dictionary search for all 25 possible steps. 

Be that as it may, several linguistic patterns and the statisticaly uneven distribution of characters in a sentence are sufficient to determine which deciphered string is more likely to be valid.

In our solution, we used a combination of:

* Statistical plausibility of a characters instance in a sentence.
* Statistical plausibility of the occurrence of words with a certain size.
* Statistical plausibility of double characters in a word.
* Linguistic patterns applicable to all words. 

Starting from a list of 25 possible steps and ruling out instances which break one of the above rules with a certain degree of certainty we detect the correct step to decipher the text.

#### Statistical plausibility of a characters instance in a sentence.
Certain characters are expected to be found more often than others. The larger the text, the stronger this expectation becomes.
By counting each instance of a character and retaining the 5 most common, we expect that those characters should be found in the list below: 

`````[ e ][ t ][ a ][ o ][ i ][ n ][ s ][ r ][ h ]`````

This rule alone, has been tested to work on completely random words, but requires a substantial number of words to work properly.
The graph below shows the effect the size of the sentence has on the success ratio of this rule.

![graph 1](https://image.ibb.co/fwJyuc/graph1.png)


#### Statistical plausibility of the occurrence of words with a certain size.
The smaller the size of the word, the less combinations of characters are possible.
There are only two possible single character words (I and A) and only a handful two character words. Additionally, three and four character words such as `The`, `And`, `This` etc. are very likely to be found in a sentence. Using a dictionary search for short words is a simple task and is very likely to yield a match.

Possible words used for this rule are loaded externally through the _beans.xml_ file for easier manipulation and possible future extension of the dictionary.

#### Statistical plausibility of double characters in a word.
Though most characters can be found doubled in a word with minor exceptions, some are more common than others.

`[ ss ][ ee ][ tt ][ ff ][ ll ][ mm ][ oo ]`

#### Linguistic patterns applicable to all words.
Apart from statistical occurrences of characters and words, a number of rules can be found which is always(?) true for all words.

* **Words must have at least one vowel**. Words (even single letter ones), must have at least one vowel. Words consisting with consonants are not valid.
* **Q must always be followed by u**. Q is must not be found followed with anything other than u, unless it is the final character of the word.
* **Words can not end with I, V or J**. Words ending with either i, v or j are not valid.