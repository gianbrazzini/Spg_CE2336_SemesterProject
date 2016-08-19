/*
 *  Written by: Gian Brazzini
 *  CS 2336.501
 *  Project 1
 *  4/15/15
 * 
 */

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class CS_Project1{
    static int file = 0;
    public static void main(String[] args) throws java.io.IOException{

        // Initiate the string array that will be passed as a reference to readFile
        // readFile will send the File gotten from getFile() as a parameter as well as the array
        // The array will return because it was passed as a reference
        String[][] inputStream = new String[500][10];
        /*readFile(new Scanner(getFile()), inputStream);
        
        // The program will then use outputHandler() to output all of the questions
        // The parameters are the same array that was returned from readFile, and the output file name (it's put there so that it's easy to change to any desired name)
        outputHandler(inputStream,"output.txt");
        System.out.print("Run Successful!\n");*/
        
        
        System.out.print("Choose which file# to run or 0 to enter unique file name: ");
        int choose = new Scanner(System.in).nextInt();
        
        switch(choose){
            case 0:     readFile(new Scanner(getFile()), inputStream);
                        outputHandler(inputStream, "output_USER.txt");
                        break;
                
            default:    file = choose;
                        File input = new File("input_output/input"+file+".txt");
                        readFile(new Scanner(input), inputStream);
                        //System.out.print("Would you like to double check your file with the example file given? (y/n)");
                        outputHandler(inputStream, "input_output/output"+file+"_USER.txt");
                        //if(new Scanner(System.in).nextLine().toLowerCase().contains("y"))
                            //check();
                        break;
        }

    }
    
    /* outputHandler is in charge of sending each question to the Q class and it's corresponding function
    // outputHandler is very simple because it only accepts one type of formatting which was done by the function readFile()
    // the question is chosen by the first dimension
    // on the second dimension are the strings that need to be written out
    // the first value of the second dimension is always the "type" of question, since all of them have different formats
    */
    static void outputHandler (String[][] inString, String outName)throws java.io.IOException{

        //to make it simpler, the PrintWriter is initiated inside this function since it doesn't need to be used anywhere els
        PrintWriter writeTo = new PrintWriter(outName);
        
        //for loop that iterates for every question in the array until the array hits null
        for(int q=1; inString[q][0]!=null ; q++){
            //The switch statement checks which type of question it is
            switch(inString[q][0])
            {  
                // Each method from the Q class accepts a different amount of variables because each one does specific things
                case "MC":  case "MR":
                            writeTo.print( Q.MC(inString[q][1],inString[q][2],inString[q][3],inString[q][4],inString[q][5]) );
                            break;
                case "MAT": case "MT":
                            writeTo.print( Q.MAT(inString[q][1],inString[q][2],inString[q][3],inString[q][4],inString[q][5]) );
                            break;
                case "ESS": case "P":
                            writeTo.print( Q.ESS(inString[q][1],inString[q][2],inString[q][3]) );
                            break;
                case "TF":  writeTo.print( Q.TF(inString[q][1],inString[q][2],inString[q][3]) );
                            break;
                case "FIB": writeTo.print( Q.FIB(inString[q][1],inString[q][2],inString[q][3],inString[q][4],inString[q][5]) );
                            break;
                case "S":   writeTo.print( Q.S(inString[q][1],inString[q][2],inString[q][3],inString[q][4],inString[q][5]) );
                            break;
                case "SR":  writeTo.print( Q.SR(inString[q][1],inString[q][2]) );
                            break;    
                default:    break;
            }
        }
        
        writeTo.close();
    }
    
    /*  This function is void becasue it only needs to return the 2 dimensional array as a reference
    //  This is the most important function of the program because if will format the input into the array 
    //  The format given from this function is in such a way that the outputter does not have much error checking to do
    //  This function will 
    //  The first dimension of the array refers to the question number (row)
    //      'int q' is used here to represent the question we are on (row)
    //  'int i' is used here to represent the each of a question (column). It could be for the type, the actual question, or each individual answer
    */
    static void readFile(Scanner reader, String[][] inputStream){
        
        String temp;
        String[] parse = new String[10];
        boolean answerList = false;
        
        for(int q=0, i=0; reader.hasNext();){
            temp = reader.nextLine();
            
            // Checks if there are any '@' or if the line is empty
            // In the case that it does, the program skips this while loop's iteration
            // It will then get the next line from the file and perform the same test when it reaches the if statement again
            if(Parser.ignore(temp))
                continue;
            
            //If the line contains one of the following lines then the program knows that it's time to move to the next question by increasing q by 1.
            // When it's "type" it has to go to the next column of the aray as was explained above
            // When it's "Answers:" it goes to the next column and stays there because "type" & "Answers:" won't be found again
                // "Answers:" is saved on the first column of the corresponding row to make it easy to find
                //
            if(temp.contains("Type:")||temp.contains("Answers:")){
                q++;
                i=0;
                
                //This checks to see if the input file gives the answers separately after all the questions.
                answerList=temp.contains("Answers:")? true:false;
            }
            
            //For every string that was returned in the parse array the 
            //In most cases, only one String was returned
            //This is just a safety method so that in case more than one significant Strings get put together accidentally
                // the program will still know that it should be a separate line.
            parse=Parser.parse(temp);
            for(int p=0;parse[p]!=null;p++){
                inputStream[q][i] = parse[p].trim();
                i++;
            }
        }
        
        //This is only necessary to run if the input file has the answers listed separately from the rest of the body.
        //It will format the input array by adding the corresponding asterisks to the correct questions so that the outputter doesn't have to 
        if(answerList)
            formatAnswersFromList(inputStream);
        
    }
    
    /* This function is only called when the readFile() function finds "Answers:" on one of the lines
    // The reason being, is that so asterisks can be added on the the correct answers
    // This program works by first finding the row that has the String "Answers:" on the first column
    // After that it will stay on that row but iterate through the columns until it hits null
    */
    static void formatAnswersFromList(String[][] inputStream){
        
        // These are given final variables because each answer is always in the same column
        // eg. B is always inputStream[q][3] which is inputStream[q][B] for any q
        final int A=2;
        final int B=3;
        final int C=4;
        final int D=5;
        final int T=2;
        final int F=3;
       
        String[][] answers = new String[500][20];
        String temp;
        int q=0, a=0, index, p=0;
        boolean found=false, mult;

        // For loops finds the row that has "Answers:" in its first column and gives 'int a' that value
        for(int i=1; ; i++){
            if( inputStream[i][0].contains("Answers:") ){
                a=i;
                found=true;
                break;
            }  
        }
        
        //As a way to doulbe check that the answers the rest of the program only runs if "Answers:" was in fact found.
        if(!found)
            return;
        
        // This for loop saves each answer, or answers in it's corresponding row of the array 'answers[][]'
        // To make things simpler for the next step, each question number corresponds to the actual row in the array
            // Eg. the answers for question 3 will be saved in answers[3][n], for n amount of correct answers
            // There will be emtpy rows
        for(int i=1; inputStream[a][i]!=null; i++){
            
            // temp is initiated as inputStream[a][i] for simplification purposes
            temp = inputStream[a][i];
            
            // q will be the question number, it get saved by getting the the numbers in the String before the '.' or ')'
            index = temp.contains(".")? temp.indexOf("."): temp.indexOf(")");
            q = Integer.parseInt(temp.substring(0,index));
            
            // Temp will equal everything in the string past the '.' or ')'
            temp = temp.substring(index+1,temp.length()).trim();
            
            // The do while loop will iterate through every answer given for the corresponding question
            // It will know if there are multiple answers by checking for commas
            // It will then save each answer into the corresponding column for the row of that question, 
            do{
                // When it has a comma it means that there are more than one answer left in the String
                // It will then save the substring from the begining fo the string up to the comma and save it to the array
                // It then shortens the string by getting the substring from the comma to the end and saving it to itself
                if(temp.contains(",")){
                    answers[q][p] = temp.substring(0,temp.indexOf(","));
                    temp = temp.substring(temp.indexOf(",")+1,temp.length()).trim(); 
                    p++;
                }
                else{
                    answers[q][p] = temp;
                    break;
                }

            }while(true);
            
            //reset p for the next set of answers
            p=0;
        }
        
        // This for loop adds asterisks to the correct answers
        // It knows how many answers are left because the variable 'q' was left as the last question
        for(int i=0, j=0; i<q+1;i++)
            // 
            for(j=0; answers[i][j]!=null; j++){
                
                // It makes each answer lower case to make the switch statement smaller
                answers[i][j] = answers[i][j].toLowerCase();
                
                // It checks to see which is the correct answer and modifies it accordingly
                // For multiple choice, it just adds an asterisk to the correct answer choices
                // For TF, it adds an asterisk to the correct answer choice but also writes the incorrect answer choice without an asterisk
                switch(answers[i][j]){
                    case "a":   inputStream[i][A] = new StringBuilder( "*" + inputStream[i][A].trim() ).toString();
                                break;
                    case "b":   inputStream[i][B] = new StringBuilder( "*" + inputStream[i][B].trim() ).toString();
                                break;
                    case "c":   inputStream[i][C] = new StringBuilder( "*" + inputStream[i][C].trim() ).toString();
                                break;
                    case "d":   inputStream[i][D] = new StringBuilder( "*" + inputStream[i][D].trim() ).toString();    
                                break;
                    case "t":   inputStream[i][T] = new StringBuilder( "*a. T").toString(); 
                                inputStream[i][F] = new StringBuilder( "b. F").toString(); 
                                break;
                    case "f":   inputStream[i][T] = new StringBuilder( "a. T").toString(); 
                                inputStream[i][F] = new StringBuilder( "*b. F").toString(); 
                                break;
                    default:    break;
                }
            }

    }
    
    /*Very simple function that gets the name of file as input form the user
    //It checks if the file exists before returning to the main function
    //If the file doesn't exist then the function asks until the user enters the name of a valid file.
    //If the filename entered by the user does not have ".txt" at the end, the function will append it.
    //If the user decides not to input a file, this function will terminate the program.
    */
    static File getFile(){
        
        Scanner input = new Scanner(System.in);
        String fileName;
        String s="";
        while(true){
            System.out.print(s + "Enter the name of the input file or (c) to cancel: ");
            fileName = input.nextLine();  
            
            //Exits if the user inputs a 'c' or a 'C'
            if((fileName.charAt(0) == 'c' || fileName.charAt(0) == 'C') && fileName.length() == 1)
                System.exit(0);
            
            //If the filename entered by the user does not contain ".txt." at the end of it, it gets added on
            if(!fileName.contains(".txt"))
                fileName+=".txt";
            
            //It returns the file to main if the input file is valid.
            if( new File(fileName).exists() )
                return new File(fileName);

            //s is blank until the program failed once, then each consecutive fail the program will output this line
            s="Not a valid input file!\n";
        }
    }

    static void check()throws java.io.IOException{

        String userName, exampleName, userTemp, exampleTemp;
        boolean good;        
        for(int i=1; i<=1; i++){
            good=true;
            int count=1;
            //file = i;
            userName = new StringBuilder("input_output/output"+file+"_USER.txt").toString();
            exampleName = new StringBuilder("input_output/output"+file+".txt").toString();

            File example = new File(exampleName);
            File user = new File(userName);
            Scanner exampleFile = new Scanner(example);
            Scanner userFile = new Scanner(user);

            System.out.println("\nFILE #" + file);

            for(; exampleFile.hasNext()&&userFile.hasNext(); ){
                userTemp = userFile.nextLine();
                exampleTemp = exampleFile.nextLine();

                while(userTemp.equals("")||exampleTemp.equals("")){
                    if(userTemp.equals(""))
                        userTemp=userFile.nextLine();
                    if(exampleTemp.equals(""))
                        exampleTemp=exampleFile.nextLine();
                }

                if( !userTemp.equals(exampleTemp) ){
                    System.out.println("Mismatch at "+count);
                    System.out.println("User: \t\t" + userTemp);
                    System.out.println("Example: \t" + exampleTemp);
                    good = false;
                    System.out.println();
                }
                count++;
            }

            if(good)
                System.out.println("Files are exact!");

        }

    }
}