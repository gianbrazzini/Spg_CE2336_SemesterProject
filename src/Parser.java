/*
 *  Written by: Gian Brazzini
 *  CS 2336.501
 *  Parser Class
 *  4/15/15
 * 
 */

// The reason for this class was to better organize the program.
// When referrring to "significant lines" all it means is that either an question and an answer, or more than one answers were inputted together from the text file
    //ie. '#)' or '#.'
// This class also functions to double check and correct that error.

public class Parser{
    
    private Parser(){}
    
    // Checks the paramter string to see if it's empty or contains '@' to see if the line should be ignored.
    static boolean ignore(String t){
        if(t.equals("")||t.equals(" ")||t.contains("@"))
            return true;
        return false;
    }
    
    // Each String sent here is checked to see if there are more than one significant lines
    static String[] parse(String t){
        
        // c is the char array of the String t, to make it easier to iterate through
        String[] p = new String[10];
        // iAt is the array where each new significant line's starting place will be stored.
        int[] iAt = new int[10];
        char[] c = t.trim().toCharArray();
        int count=0;
        
        // Checks to see how many significant lines there are by checking for '#)' or '#.'
        for(int i=0; i<c.length-2;i++){
            if(c[i]=='.'||c[i]==')')
                if(isABCD(c[i-1])|| (c[i-1] >= '0' && c[i-1] <= '9')){
                    if(i-1!=0)
                        if(c[i-2]=='*')
                            iAt[count]=i-2;
                    else
                        iAt[count]=i-1;
                    count++;
                }
        }
        count--;
        
        // if count>0 it means there were multiple significant lines 
        // iAt[] will serve as an index to know where the significant lines start and end
        // it will know how many iterations to go through because of count
        if(count>0){
            for(int i=0;i<count;i++){
                // it will append the line from iAt to the next iAt to p[i]
                p[i]="";
                append(p,i,c,iAt[i],iAt[i+1]-1); 
            }
            p[count]="";
            append(p,count,c,iAt[count],c.length-1);
        }
        // otherwise, the entire line gets appended and returned
        else{
            p[0]="";
            append(p,0,c,0,c.length-1);
        } 
        
        // Trims every line and checks to see if "Type:" is contained in any of them
        // If "Type:" is contained then "Type:" is taken off so that the only thing that remains is the actual question type
        for(int i=0;p[i]!=null;i++){
            p[i]=p[i].trim();
            if(p[i].contains("Type:"))
                p[i]= p[i].substring( 6,(p[i].length()) );
        }

        return p;
    }   
    
    //Checks to see if the character is a letter that could be the start to a significant line
    //If it is then the parse function will know to check for the next 
    private static boolean isABCD(char c){
        if(c=='A'||c=='B'|c=='C'||c=='D'||c=='a'||c=='b'|c=='c'||c=='d')
            return true;
        else return false;
    }
    
    // Turns the character array c from 'start' to 'end', as a String into p[i]
    private static void append(String[] p, int i,char[] c, int start, int end){
            p[i]+=c[start];
            if(start!=end)
                append(p,i,c,start+1,end);
    }
    
}