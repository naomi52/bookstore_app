/*============================================================================
CustTotal: A JDBC APP to list total sales for a customer from the YRB DB.



Parke Godfrey
*2013 March 26	[revised]
2004 March		[original]
============================================================================*/



import java.util.*;
import java.net.*;
import java.text.*;
import java.lang.*;
import java.io.*;
import java.sql.*;



/*============================================================================


CLASS CustTotal


============================================================================*/



public class proj2 {


    private Connection conDB;   // Connection to the database system.

    private String url;         // URL: Which database?

    private Integer custID;     // Who are we tallying?

    private String  custName;   // Name of the customer.

    private String custCity; 	//city of the customer.

    private String category;

    private String chosencat;

    private String chosentitle;
    
    private String chosentitle1;

    private Integer chosenyear;

    private String cbyear;

    private String cblang;

    private String cbweight;

    private String cbclub;
    
    private String cbname;

    private String custAnswer;
    
    private String updateAnswer;
    
    private String updateAnswerName;
    
    private String updateAnswerCity;

    private double cumprice;

    private Integer quantity;

    private double cbmin;

    private UUID uuid;

    private int chosenCatno;
    HashMap<String, Integer> catList = new HashMap<String, Integer>();  

    // Constructor


    public proj2 (String[] args) {
        // Set up the DB connection.
        try {
            // Register the driver with DriverManager.
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(0);


        }
        // URL: Which database?
        url = "jdbc:db2:c3421a";

        // Initialize the connection.

        try {
            // Connect with a fall-thru id & password
            conDB = DriverManager.getConnection(url);

        } catch(SQLException e) {
            System.out.print("\nSQL: database connection error.\n");
            System.out.println(e.toString());
            System.exit(0);
        }    

        // Let's have autocommit turned off.  No particular reason here.


        try {
            conDB.setAutoCommit(false);
            
        } catch(SQLException e) {
            System.out.print("\nFailed trying to turn autocommit off.\n");
            e.printStackTrace();
            System.exit(0);
        }    


        System.out.println("|------->Please provide customer ID below. Only numbers are acceptable!");
        //System.out.println("");
        Scanner input = new Scanner(System.in);
        custID = input.nextInt();       

        while (!customerCheck()) {
            System.out.print("|------->There is no customer #");
            System.out.print(custID);
            System.out.println(" in the database.");
            System.out.println("|------->Please provide customer ID:");
            custID = input.nextInt();       

        }

		//Ask for update if customer exists
		if (customerCheck()) 
		{			
			System.out.println("Hi " + custID + " " + custName + " " + custCity);
            System.out.println("Will you like to update your information? :  Enter Y or N");
			Scanner input1 = new Scanner(System.in);
			updateAnswer = input1.nextLine();

			if(updateAnswer.equals("Y") || updateAnswer.equals("y") )
			{
				System.out.println("You said yes");	
				System.out.println("Please pick an option below");				
				System.out.println("i) Update your name");				
				System.out.println("ii) Update your city");					
				Scanner input2 = new Scanner(System.in);
				updateAnswer = input2.nextLine();
				
				while(!(updateAnswer.equals("i")) && !(updateAnswer.equals("ii")) && !(updateAnswer.equals("done")))
				{
					System.out.println("Enter \"i\" or \"ii\" ");
					System.out.println("Enter \"done\" if you changed your mind");					
					Scanner input3 = new Scanner(System.in);
					updateAnswer = input3.nextLine();
				}
				
				if(updateAnswer.equals("i"))
				{
					System.out.println("Enter your new name");					
					Scanner input4 = new Scanner(System.in);
					updateAnswerName = input4.nextLine();					
					custName = updateAnswerName;					
					updateName();
					
					System.out.println("Update done!");
					System.out.println("Will you like to update your information again? :  Enter Y or N");
					Scanner inputloop1 = new Scanner(System.in);
					updateAnswer = inputloop1.nextLine();
					
					while(updateAnswer.equals("Y") || updateAnswer.equals("y") )
					{	
					System.out.println("You said yes");	
					System.out.println("Please pick an option below");				
					System.out.println("i) Update your name");				
					System.out.println("ii) Update your city");					
					Scanner inputbla = new Scanner(System.in);
					updateAnswer = inputbla.nextLine();
					if(updateAnswer.equals("i"))
					{
					System.out.println("Enter your new name");					
					Scanner inputloop2 = new Scanner(System.in);
					updateAnswerName = inputloop2.nextLine();					
					custName = updateAnswerName;					
					updateName();
					System.out.println("Update done!");
					}
					else if(updateAnswer.equals("ii"))
					{
					System.out.println("Enter your new city");					
					Scanner input9 = new Scanner(System.in);
					updateAnswerCity = input9.nextLine();					
					custCity = updateAnswerCity;				
					updateCity();
					System.out.println("Update done!");
					}
					else
					{
						System.out.println("This is not a valid option");
					}
					System.out.println("Update done!");
					System.out.println("Will you like to update your information again? :  Enter Y or N");
					Scanner inputloop3 = new Scanner(System.in);
					updateAnswer = inputloop3.nextLine();														
					}
				
				}
				
				else if(updateAnswer.equals("ii"))
				{
					System.out.println("Enter your new city");					
					Scanner input5 = new Scanner(System.in);
					updateAnswerCity = input5.nextLine();					
					custCity = updateAnswerCity;				
					updateCity();
					
					System.out.println("Will you like to update your information again? :  Enter Y or N");
					Scanner inputloop4 = new Scanner(System.in);
					updateAnswer = inputloop4.nextLine();
					
					while(updateAnswer.equals("Y") || updateAnswer.equals("y") )
					{	
					System.out.println("You said yes");	
					System.out.println("Please pick an option below");				
					System.out.println("i) Update your name");				
					System.out.println("ii) Update your city");					
					Scanner inputblab = new Scanner(System.in);
					updateAnswer = inputblab.nextLine();
					if(updateAnswer.equals("ii"))
					{
					System.out.println("Enter your new city");					
					Scanner inputloop5= new Scanner(System.in);
					updateAnswerCity = inputloop5.nextLine();					
					custCity = updateAnswerCity;				
					updateCity();
					}
					else if(updateAnswer.equals("i"))
					{
					System.out.println("Enter your new name");					
					Scanner inputloop11 = new Scanner(System.in);
					updateAnswerName = inputloop11.nextLine();					
					custName = updateAnswerName;					
					updateName();
					}
					else
					{
						System.out.println("This is not a valid option");
					}
					System.out.println("Will you like to update your information again? :  Enter Y or N");
					Scanner inputloop8 = new Scanner(System.in);
					updateAnswer = inputloop8.nextLine();									
					
					}
				}					
				else
				{
						
				}
			} 
			
			else if(updateAnswer.equals("N") || updateAnswer.equals("n") )
			{
				System.out.println("You said no");
			}  
			
			else
			{
				System.out.println("That was an invalid answer, out of the update you go!");
			}  

        }
        //end of customer information update



        // Report total sales for this customer.


        find_customer();
        fetch_categories();
        find_book();
        min_price(); 
        clubminprice();
        insert_purchase();
        dothis();

        try {
            conDB.commit();

        } catch(SQLException e) {
            System.out.print("\nFailed trying to commit.\n");
            e.printStackTrace();
            System.exit(0);
        }    
        // Close the connection.

        try {
            conDB.close();

        } catch(SQLException e) {
            System.out.print("\nFailed trying to close the connection.\n");
            e.printStackTrace();
            System.exit(0);
        }    


    }

    public void insert_purchase()

    {
        System.out.println("Will you like to buy this book? :  Enter Y or N");
        Scanner input1 = new Scanner(System.in);
        custAnswer = input1.nextLine();
        System.out.println(custAnswer);


    //if(custAnswer == "Y" || custAnswer == "y" )

        if(custAnswer.equals("Y") || custAnswer.equals("y") )

    {

        System.out.println("You said yes");
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.
        Statement statement = null;
        queryText =
                "INSERT INTO yrb_purchase(cid, club, title, year, when, qnty) "

              + "VALUES( ?, ?, ?, ?, ?, ?)";           
        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }
        // Execute the query.


        try {


            querySt.setInt(1, custID.intValue());
           querySt.setString(2, cbclub.toString());
            querySt.setString(3, chosentitle1.toString());
            querySt.setInt(4, chosenyear.intValue());
           querySt.setTimestamp(5, getCurrentTimeStamp());
            querySt.setInt(6, quantity.intValue());
            querySt.executeUpdate();


        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }
        // Any answer?

    }

    else  if(custAnswer.equals("N") || custAnswer.equals("n") )

    {
        System.out.println("You said no, Bye!");
        System.exit(0);
    }

    else
    {
        System.out.println("Invalid string. Enter Y or N");

        custAnswer = input1.nextLine();

        while( (custAnswer != "Y" && custAnswer != "y" && custAnswer != "N" && custAnswer == "n"))

        {
            System.out.println("Invalid string. Enter Y or N");
            custAnswer = input1.nextLine();          
        }
        if(custAnswer == "Y" || custAnswer == "y" )
        {
        }
        else if(custAnswer == "N"|| custAnswer == "n")
        {
            System.exit(0);
        }
    }

    }


    public boolean customerCheck() {
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.
        boolean           inDB      = false;  // Return.
        queryText =
            "SELECT name , cid, city      "
          + "FROM yrb_customer "
          + "WHERE cid = ?     ";

        // Prepare the query.

        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }



        // Execute the query.


        try {
            querySt.setInt(1, custID.intValue());
            answers = querySt.executeQuery();

        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }



        // Any answer available?


        try {
            if (answers.next()) {
                inDB = true;
                custName = answers.getString("name");
                custCity = answers.getString("city");

            } else {
                inDB = false;
                custName = null;
            }

        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);


        }



        // Close the cursor.


        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.


        try {
            querySt.close();

        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }
        return inDB;
    }

 

    public boolean catCheck() {
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.
        boolean           inDB      = false;  // Return.
        queryText =
            "SELECT cat     "
          + "FROM yrb_category "
          + "WHERE cat = ? ";

        // Prepare the query.


        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.


        try {
            querySt.setString(1, chosencat.toString());
            answers = querySt.executeQuery();
           
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answeravailable?

        try {
            if (answers.next()) {
                inDB = true;
            } else {
                inDB = false;
            }


        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Close the cursor.


        try {
            answers.close();

        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }



        // We're done with the handle.


        try {
            querySt.close();

        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }
        return inDB;


    }

    public boolean titleCheck() {
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.
        boolean           inDB      = false;  // Return.
        queryText =
            "SELECT title, year, weight, language   "
          + "FROM yrb_book "
          + "WHERE UPPER(cat) = ? "
          + "AND UPPER(title) = ?";
        // Prepare the query.


        try 
			{
            querySt = conDB.prepareStatement(queryText);
			} 
        catch(SQLException e) 
			{
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
			}

        // Execute the query.


        try {
            querySt.setString(1, chosencat.toString().toUpperCase());
            querySt.setString(2, chosentitle.toString().toUpperCase());
            answers = querySt.executeQuery();
			} 
        catch(SQLException e)
			{
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
			}
      // Any answer?
       try {
            if (answers.next()) {
                inDB = true;
                cbyear = answers.getString("year");
                cblang = answers.getString("language");
                cbweight = answers.getString("weight");
                chosentitle1 = answers.getString("title");


            } else {
                inDB = false;
                cbyear = null;
            }


        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }        
        return inDB;
    }
 


    public String fetch_categories()
    {
    int count = 1;
   // HashMap<String, Integer> catList = new HashMap<String, Integer>();

        String stmt = "SELECT cat     "
          + "FROM yrb_category";
          
        try {    PreparedStatement nstmt = conDB.prepareStatement(stmt);
        ResultSet result = nstmt.executeQuery();

        while(result.next())

        {
           category = result.getString("cat");
            catList.put(category,count);
            System.out.println("-->"+count + "   " +category);
            count++;
        }

        }

        catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        System.out.println("|------->Please select a CATEGORY NUMBER from the list above. Has to be a number!");
        Scanner input = new Scanner(System.in);
        chosenCatno= input.nextInt();

        for (String item: catList.keySet()) {
        while(!(catList.containsValue(chosenCatno)))
        {
        	 System.out.print("|------->There is no category ");
             System.out.print(" \" " + chosenCatno + " \" ");
             System.out.println(" in the database.");
             System.out.println("|------->Please select a CATEGORY NUMBER from the list above:");
             chosenCatno= input.nextInt();
        }
        if( catList.get(item) == chosenCatno)
        {
        chosencat = item;
        }
}
 System.out.println("You chose " + chosencat);      
        return chosencat;
    }

    

 public void find_book()
    
    {
    	System.out.println("|------->What is the TITLE of the book you'd like? ");

        Scanner input = new Scanner(System.in);

        chosentitle = input.nextLine(); 
       
    	 String            queryText = "";     // The SQL text.
         PreparedStatement querySt   = null;   // The query handle.
         ResultSet         answers   = null;   // A cursor.

         queryText =
             "SELECT title as mytitle, year, language, weight         "
           + "    FROM yrb_book           "
           + "    WHERE UPPER(title) = ?                      "
           + "      AND UPPER(cat) = ?";
         try {
        	 
             querySt = conDB.prepareStatement(queryText);
         } catch(SQLException e) {
             System.out.println("SQL#2 failed in prepare");
             System.out.println(e.toString());
             System.exit(0);
         }

         // Execute the query.
         try {
            // querySt.setInt(1, custID.intValue());
        	 querySt.setString(1, chosentitle.toString().toUpperCase());
             querySt.setString(2, chosencat.toString().toUpperCase());
             answers = querySt.executeQuery();
         } catch(SQLException e) {
             System.out.println("SQL#2 failed in execute");
             System.out.println(e.toString());
             System.exit(0);
         }
         
         while (!titleCheck()) {
             System.out.print("|------->There is no book ");
             System.out.print(" \" " + chosentitle +" \" " + "under the category" + " \" " + chosencat + " \" ");
             System.out.println(" in this database.");           
             fetch_categories();            
             System.out.println("|------->Please enter the TITLE of the book you'd like");
             chosentitle = input.nextLine();          
             
         }
         try {
             if (answers.next()) {
				chosentitle1 = answers.getString("mytitle");
             } 
             else {
             }


         } catch(SQLException e) {
             System.out.println("SQL#1 failed in cursor.");
             System.out.println(e.toString());
             System.exit(0);
         }

         System.out.println("Below are the book(s) that satisfy your chosen parameters");         
         System.out.println("|------->" + "TITLE: " + chosentitle1 + "  YEAR: " + cbyear + "  LANGUAGE: " + cblang + "  WEIGHT: " + cbweight);

    }

    public void dothis()   

    {             
        String stmt =  
        "SELECT *        "
         + "    FROM yrb_purchase         ";

        try {    PreparedStatement nstmt = conDB.prepareStatement(stmt);
        ResultSet result = nstmt.executeQuery();
        ResultSetMetaData rsmd = result.getMetaData();
        int columnsNumber = rsmd.getColumnCount();


        while (result.next()) {
            for(int i = 1; i < columnsNumber; i++)
                System.out.print(result.getString(i) + " ");
            System.out.println();
        }

        }

        

        catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }
                     
    }

    public void find_customer() {  
        System.out.println("|-------> " +"ID:" + custID + "   " + "NAME: " + custName + "   " + "CITY: " + custCity);      
    }

    public void min_price()

    {
         System.out.println("|------->For a proper selection, Kindly enter the year of the book you want from the above options. Has to be a number!" );
         /*The key is a title and year so no two titles have the same year*/
         Scanner input = new Scanner(System.in);
         chosenyear = input.nextInt();        
         String            queryText = "";     // The SQL text.
         PreparedStatement querySt   = null;   // The query handle.
         ResultSet         answers   = null;   // A cursor.
         queryText =               
                 "SELECT min(price) as minimum, year, title "
               + "FROM yrb_offer yo, yrb_member ym "
               + "WHERE UPPER(title) = ? "
               + "AND year = ?"
               + "AND ym.cid = ?"
               + "AND yo.club = ym.club "
              +  "GROUP BY title, year";
         // Prepare the query.

         try {
            querySt = conDB.prepareStatement(queryText);


         } catch(SQLException e) {
             System.out.println("SQL#1 failed in prepare");
             System.out.println(e.toString());
             System.exit(0);
         }

         // Execute the query.
         try {
             querySt.setString(1, chosentitle.toString().toUpperCase());
             querySt.setInt(2, chosenyear.intValue());
             querySt.setInt(3, custID.intValue());
             answers = querySt.executeQuery();


         } catch(SQLException e) {
             System.out.println("SQL#1 failed in execute");
             System.out.println(e.toString());
             System.exit(0);
         }

         // Any answer available?

         try {
             if (answers.next()) {
                 cbmin = answers.getDouble("minimum");
             } 
             else {
             }

         } catch(SQLException e) {
             System.out.println("SQL#1 failed in cursor.");
             System.out.println(e.toString());
             System.exit(0);


         }

         System.out.println("The minimum price is $" + cbmin  + ".");
         System.out.println("|------->Please Enter the quantity you would like to purchase. Has to be a number!" );
         quantity = input.nextInt(); 
         cumprice = quantity * cbmin; 
         System.out.println("The total price is $" + cumprice  + ".");

    }

    public String clubminprice()

    {
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.

		queryText =
         "SELECT ym.club as theclub "
       + "FROM yrb_offer yo, yrb_member ym "
       + "WHERE UPPER(title) = ? "
       + "AND year = ?"
       + "AND ym.cid = ?"
       + "AND yo.price = ? "
       + "AND yo.club = ym.club "  ;

        // Prepare the query.


         try {
             querySt = conDB.prepareStatement(queryText);


         } catch(SQLException e) {
             System.out.println("SQL#1 failed in prepare");
             System.out.println(e.toString());
             System.exit(0);


         }



         // Execute the query.


         try {
             querySt.setString(1, chosentitle.toString().toUpperCase());
             querySt.setInt(2, chosenyear.intValue());
             querySt.setInt(3, custID.intValue());
             querySt.setDouble(4, cbmin );
             answers = querySt.executeQuery();


         } catch(SQLException e) {
             System.out.println("SQL#1 failed in execute");
             System.out.println(e.toString());
             System.exit(0);


         }



         // Any answer?


         try {
             if (answers.next()) 
             {
                 cbclub = answers.getString("theclub");
             } 
             else {
             }


         } catch(SQLException e) {
             System.out.println("SQL#1 failed in cursor.");
             System.out.println(e.toString());
             System.exit(0);
         }

         return cbclub;

    }
	
	public void updateName()
	{
		String            queryText = "";     // The SQL text.
		PreparedStatement querySt   = null;   // The query handle.
		queryText =        
          "   UPDATE yrb_customer " +
          "   SET name = ? "  +
          "   WHERE cid = ? ";
        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);

        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
       }

        // Execute the update.
        try {
            querySt.setString(1, updateAnswerName);
            querySt.setInt(2, custID.intValue());
			querySt.executeUpdate();
        } 
        catch(SQLException e) {
          System.out.println("SQL#1 failed in execute");
		  System.out.println(e.toString());
		  System.exit(0);
		}
         
	}
	
		public void updateCity()
	{
		String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
		queryText =         
          "   UPDATE yrb_customer " +
          "   SET city = ? "  +
          "   WHERE cid = ? ";
		// Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);


        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
       }
		// Execute the update.


        try {
            querySt.setString(1, updateAnswerCity);
            querySt.setInt(2, custID.intValue());
			querySt.executeUpdate();

        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
			System.exit(0);
        }
	}
	
	
    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
	

    public static void main(String[] args) {


        proj2 ct = new proj2(args);


    }


}
