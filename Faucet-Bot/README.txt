FAUCET BOT  -  README.txt
-------------------------------------
Faucet Bot uses Selenium and Google Chrome Driver to launch a webpage and automate interaction with the site simulating
a human. 

Bitcoin wallet addresses must be added to the appropriate array in the ChromeBotDriver class. For each address in the array,
a new Web Driver bot will be created and continue to make claims on the faucet until the desired number of claims is made.
User will be prompted with an input dialog box asking for a desired number of claims to be made (per address) on program start. 

The Google Chrome Driver .exe file has been included in the folder for convenience.

The jar files for Selenium external libraries, etc... can be found in the lib folder.

Wallet addresses must be input into the "WALLET-ADDRESS-CONFIG.txt" file for the program to work.
Enter each address on its own line (beginning at very first line) and add no extra characters or
spaces before or after the address or between the file lines.