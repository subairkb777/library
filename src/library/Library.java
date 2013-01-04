
package library;

import java.sql.*;

public class Library {
   static private  Connection connection1; 
  public  Library()
    {  try
         {     
              connection1=Connector.connectDataBase("library", "root", "");
     }
      catch(Exception e)
              {  System.out.println(e);
    }
    }
   public  Library(int i){
       
   }
    /**Sarath
     *maxDate variable contains the maximum allowed days to expire the book issued,
     * default is 30, defined by the constructor.
     *
     */
        private static boolean privilage=false;
    private static int maxDate=30, bookLimit=7,maxDays=30,incrementFine=2;
    private static float startingFine=.5f;
    private static String loginInfo="logininfo",issuedInfo="issuedinfo", studentInfo="studentinfo",bookInfo="bookinfo";
    
    /**
     *issueBook will insert issued book details to table issuedInfo
     * and will return the status
     *
     */
    
    public String issueBook(String rollNo,String bookId){
       try{
        
        PreparedStatement statement1 = connection1.prepareStatement("select indexBook  from "+bookInfo+" where bookId='"+bookId+"'");
        ResultSet s2=statement1.executeQuery();
         s2.next();
         int indexBook=s2.getInt(1);
        PreparedStatement statement = connection1.prepareStatement("select indexStudent,totalBooks from "+studentInfo+" where rollNumber='"+rollNo+"'");
           
        ResultSet s1=statement.executeQuery();
        s1.next();
        int indexStudent= s1.getInt(1);
        int totalBooks=s1.getInt(2);
        if(!(totalBooks > 6)){
        int k=statement.executeUpdate("insert into "+issuedInfo+" ('"+indexBook+"','"+indexStudent+"','CURDATE()'");
        if(k<=0){
            return "Error in insertion into "+issuedInfo+" ";
            
        }
        }else{
            return "You reached your issue limit";
            
        }
      }catch(Exception e){
          e.printStackTrace();
            return "Error in Inserting issued Book";
            
            
        }
        return "Successfull...";
    
    }
    
    /**
     *renewBook will renew issued book details to table issuedInfo
     * 
     * If there is any dues it will print the amount to be paid and won't allow to 
     * renew the book
     *
     * differenceInDate variable contains the difference between current date and
     * default date
     */
    
    public String renewBook(int indexBook){
        try{
        PreparedStatement statement2=connection1.prepareStatement("SELECT DATE_ADD(issuedDate,INTERVAL 30 DAY) FROM "+issuedInfo+" ");
        ResultSet expire= statement2.executeQuery();
        expire.next();
        String expireDate=expire.getString(1);
        int differenceInDate=statement2.executeQuery("SELECT DATEDIFF('"+expireDate+"','CURDATE()'").getInt(1);
        if(differenceInDate>maxDate){
           return "You have dues of rupees:"+((differenceInDate-maxDate)*.5)+"Can't issue books";
           
           
       }else{
       
          int s2=statement2.executeUpdate("update "+issuedInfo+"  set issuedDate='CURDATE()' where indexBook="+indexBook);
          if(s2<=0){
              return "Error in updating the date";
          }
       }
    }catch(Exception e){
        
    }
        return "Renew Succefully...";
    }

    /**
     *returnBook will remove issued book details from table issuedInfo
     * and decrement totalBooks by one
     *
     */
   public String returnBook(int indexBook){
         try{
             PreparedStatement statement = connection1.prepareStatement("select rollNo from "+studentInfo+" where indexBook="+indexBook);
           
        ResultSet s3=statement.executeQuery();
             
         
         
         s3.next();
         int rollNo=s3.getInt(1);
        statement= connection1.prepareStatement("delete from issuedBook where indexBook="+indexBook);
         int s2=statement.executeUpdate();
         statement =connection1.prepareStatement("select totalBooks from "+studentInfo+" where rollNo="+rollNo);
         ResultSet s1=statement.executeQuery();
         s1.next();
         int totalBooks=s1.getInt(1);
         statement=connection1.prepareStatement("update "+studentInfo+" set totalBooks="+(totalBooks-1));
         statement.executeQuery();
         if(s2<=0){
             return "Error in updating the date";
         }
        
    }catch(Exception e){
        
    }
        return "Successfull";
    }
    /**
     * *sarath END
     */
    /**
     * subair
     *Can Search by author,bookName,bookId
     *Function will return the query result
     *
     */
    
    ResultSet searching (String x , int y) throws SQLException
    {
        
        if(y == 1)
        {
          PreparedStatement statement1 = connection1.prepareStatement("select indexBook  from "+bookInfo+" where author ='"+x+"'");
         ResultSet s2=statement1.executeQuery();
         s2.next();
         int iBook=s2.getInt(1);
           // System.out.println("indexbook = "+iBook);
          PreparedStatement statement2 = connection1.prepareStatement("select indexStudent  from "+issuedInfo+" where indexBook ='"+iBook+"'");
         ResultSet s3=statement2.executeQuery();
         s3.next();
         int iStudent=s3.getInt(1);
           // System.out.println("indexstudent = "+iStudent);
            String query = "select bookName,bookId,author,department,inOut1,s.name"
                  + " from "+bookInfo+" , "+studentInfo+" as s where author like '%"+x+"%' AND s.indexStudent="+iStudent;
            
          PreparedStatement statement =connection1.prepareStatement(query );
         
          ResultSet result = statement.executeQuery();
          return result;
        }
        else if(y == 2)
        {
            PreparedStatement statement1 = connection1.prepareStatement("select indexBook  from "+bookInfo+" where bookName ='"+x+"'");
         ResultSet s2=statement1.executeQuery();
         s2.next();
         int iBook=s2.getInt(1);
            System.out.println("indexbook = "+iBook);
          PreparedStatement statement2 = connection1.prepareStatement("select indexStudent  from "+issuedInfo+" where indexBook ='"+iBook+"'");
         ResultSet s3=statement2.executeQuery();
         s3.next();
         int iStudent=s3.getInt(1);
            PreparedStatement statement =connection1.prepareStatement("select bookName,bookId,author,department,inOut1,s.name"
                  + " from "+bookInfo+" , "+studentInfo+" as s where bookName ='"+x+"' AND s.indexStudent="+iStudent );
           ResultSet result = statement.executeQuery();
           return result;
        } 
        else
        {
           PreparedStatement statement1 = connection1.prepareStatement("select indexBook  from "+bookInfo+" where bookId ='"+x+"'");
         ResultSet s2=statement1.executeQuery();
         s2.next();
         int iBook=s2.getInt(1);
            System.out.println("indexbook = "+iBook);
          PreparedStatement statement2 = connection1.prepareStatement("select indexStudent  from "+issuedInfo+" where indexBook ='"+iBook+"'");
         ResultSet s3=statement2.executeQuery();
         s3.next();
         int iStudent=s3.getInt(1);
            PreparedStatement statement =connection1.prepareStatement("select bookName,bookId,author,department,inOut1,s.name"
                  + " from "+bookInfo+" , "+studentInfo+" as s where bookId ='"+x+"' AND s.indexStudent="+iStudent );
           //statement.setString(1,x);
           ResultSet result = statement.executeQuery();
           return(result);
        }
    }
    /**
     *Function to search student info using rollNumber
     *Function will return the query result
     *
     */
    ResultSet getStudentInfo (String rollNum) throws SQLException{
        PreparedStatement statement =connection1.prepareStatement("select b.bookName,i.issueDate"
                + " from "+bookInfo+" AS b,"+studentInfo+" AS s,"+issuedInfo+" AS i"
                + " where s.rollNumber=? AND s.indexStudent=i.indexStudent AND b.indexBook=i.indexBook");
        statement.setString(1,rollNum);
        ResultSet result = statement.executeQuery();
           return(result);
    }
   /**
    * subair End
    */
    /*
     * Praveen
     */
     /** Insert function is for inserting student details
     * 
     */
    boolean Insert(String name, String rollno)
    {  try
    {    // Connection sampleCon=Connector.connectDataBase("library", "root", "");
         
           PreparedStatement state; // TODO add your handling code here:
            state = connection1.prepareStatement("insert into "+studentInfo+" (name,rollNo) values (?,?)");
            state.setString(1,name);
            state.setString(2,rollno);
           int k=state.executeUpdate();
            if (k==1)
            {  return true;
            }
            else
            { 
               return false;
            }
    }
    catch(Exception e)
    {
        System.out.println(e);
        return false;
    }
    
        }
    
    /** Delete function is for deleting the details of student 
     * 
     */
    boolean Delete(String rollno)
    {
         try  {    
                      Statement state=connection1.createStatement();
                      int k=state.executeUpdate("delete from "+studentInfo+" where id="+rollno);
                      if (k==1)
                      { return true;
                      }
                      else
                      { return false;
                      }
           }    
        catch(Exception e){
      System.out.println(e);
      return false;
    }
}
    /** Update function is for correcting details of student
     * 
     * @param rollno 
     */
   boolean Update(String rollno,String name,int book) 
   {  try  {    
                      Statement state=connection1.createStatement();
                      int k=state.executeUpdate("update  "+studentInfo+"  set name='"+name+"' totalBooks="+book+" where rollNumber="+rollno+"");
                      if (k==1)
                          return true;
                      else
                          return false;
}
 catch(Exception e){
      System.out.println(e);
      return false;
 }  
 }
 /** bookInsert function is to add a new book to the database
  * 
  */
   
   boolean bookInsert(String bookName,int bookId,String author,String department)
   {   try  {    
                      Statement state=connection1.createStatement();
                int k=  state.executeUpdate("insert into "+studentInfo+" values('"+bookName+"', "+bookId+",'"+author+"','"+department+")");
                if (k==1)
                          return true;
                      else
                          return false;
   }
   
   catch(Exception e)
   {  System.out.println(e);
      return false;
   }
   }
   /** bookDelete function is used to delete a book from the database
    * 
    * @param bookId 
    */
    boolean bookDelete(int bookId)
    {  
         try  {    
                      Statement state=connection1.createStatement();
                      int k=state.executeUpdate("delete from "+bookInfo+" where bookId="+bookId);
                      if (k==1)
                          return true;
                      else
                          return false;
                      
           } 
         
        catch(Exception e){
      System.out.println(e);
      return false;
    }
         /**
          * Praveen end
          */
        
}
     /**
    * shibin End
    */
    public  boolean libraryLogin(String userName,String passwd) throws SQLException
    {
        
       ResultSet result;
        PreparedStatement preparedStatement =connection1.prepareStatement(
                "select * from "+loginInfo +" where userName=? and password=? ");
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, passwd);
        result=preparedStatement.executeQuery();
        if(result.next())
        {
            if(result.getInt("isAdmin")==1)
            {
                privilage=true;
                return true;
            }
        }
        return false;
       
        
    }
    
    public boolean addNewAdmin(String newUser,String newPasswd,byte privilage) 
    {
        Statement statement;
        ResultSet resutlt;
        
        if(newUser.isEmpty()) return false;
        try{
        statement=connection1.createStatement();
        
        if(statement.executeUpdate("insert userName,passWord,isAdmin into "+loginInfo+""
                + "values ( "+newUser+" , "+newPasswd+" , "+privilage +");"
                          )==1
                          )
            return true;
        else return false;
      
        }
        catch(SQLException e)
        {
            return false;
        }
    }
    
    public boolean close()
    {
        return Connector.close(connection1);
    }
  
}
