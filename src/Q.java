/*
 *  Written by: Gian Brazzini
 *  CS 2336.501
 *  Q Class
 *  4/15/15
 * 
 */

/*  This class was made to organize the program better
//  It's purpose is sole for outputting one question at at time
//  The switch statement that sends each qustion here sends it to the correct type of question
//  Each function is very simple and does very little to error check because most of the formatting has alread been done by the previous functions that handled the array
*/

public class Q{
    
    private Q(){}
    
    //Matching
    static String MC(String question, String answer1, String answer2, String answer3, String answer4){
        
        int count;
        if(!answer4.equals(""))
            count=4;
        else
            count=3;
        
        String[] answers = {answer1, answer2, answer3, answer4};

        for(int i=0; i<count;i++){

            answers[i] = trimP(answers[i]);
            
            if(answers[i].contains("*"))
                answers[i] += "\tcorrect\t";
            else
                answers[i] += "\tincorrect\t";
            
            answers[i] = trimA(answers[i]);
        }
        
        return new StringBuilder("MC\t"+ trimQ(question) +"\t"+ answers[0] +"\t"+ answers[1] +"\t"+ answers[2] +"\t"+ answers[3] +"\r\n").toString();
    }
    
    //True False
    static String TF(String question, String answer1, String answer2){
        
        String correctAnswer = answer1.contains("*")? answer1:answer2;
        correctAnswer = correctAnswer.contains("T")? "TRUE":"FALSE";
        
        return new StringBuilder("TF\t" +trimQ(question)+ "\t"+ correctAnswer+ "\r\n").toString();
    }
    
    //Matching
    static String MAT(String title, String question, String answer1, String answer2, String answer3){
        
        int count;
        if(!answer3.equals(""))
            count=3;
        else
            count=2;
        
        String[] answers = {answer1,answer2,answer3};
        String temp;
        int j;
        
        for(int i=0;i<count;i++){
            answers[i]=trimA(answers[i]).trim();
            j = answers[i].indexOf("=");
           
            temp = answers[i].substring(j+1,answers[i].length()).trim();
            answers[i] = answers[i].substring(0,j-1).trim();
            
            answers[i]= new StringBuilder(answers[i].trim() +"\t"+ temp.trim()).toString().trim();
        }
        
        if(count==2)
            return new StringBuilder("MAT\t"+ trimQ(question) +"\t"+ answers[0] +"\t"+ answers[1] +"\r\n").toString();
        else
            return new StringBuilder("MAT\t"+ trimQ(question) +"\t"+ answers[0] +"\t"+ answers[1] +"\t"+ answers[2] +"\r\n").toString();
    }
    
    //Essay
    static String ESS(String title, String question, String sampleAnswer){
        
        String a = "";
        if(!(sampleAnswer.equals("")||sampleAnswer==null||sampleAnswer.equals(" ")))
            a = "\t"+trimA(sampleAnswer);
            
        return new StringBuilder("ESS\t" +trimQ(question)+ a +"\r\n").toString();
    }
 
    //Fill in the blank
    static String FIB(String title, String question, String answer1, String answer2, String answer3){
        return new StringBuilder("FIB\t"+title+"\t"+ trimQ(question) +"\t"+answer1+"\t"+answer2+answer3+ "\r\n").toString();
    }

    //Fill in the blank
    static String S(String title, String question, String answer1, String answer2, String answer3){
        return new StringBuilder("FIB\t"+ trimQ(question) +"\t"+ trimA(answer1) +"\t"+ trimA(answer2) +"\t"+ trimA(answer3) +"\r\n").toString();
    }
   
    //
    static String SR(String question, String sampleAnswer){
        return new StringBuilder("SR\t"+question+"\t"+sampleAnswer+"\t").toString();
    }
    
    private static String trimP(String question){

        if(question==null)
            return null;
        
        int index = question.indexOf(".");
        index = question.indexOf(".",index+1);
        
        if(index!=-1)
            return question.substring(0,index).trim();
        
        return question.trim();
    }
    
    private static String trimQ(String question){
        
        if(question==null)
            return null;
        
        int i = question.contains(")")? question.indexOf(")"):question.indexOf(".");
        i+=1;
                
        return question.substring(i,question.length()).trim();
    }
    
    private static String trimA(String answer){
        if(answer==null)
            return null;

        if(answer.contains("*"))
            return (answer.substring(4,answer.length())).trim();
        else
            return (answer.substring(3,answer.length())).trim();
    }
}