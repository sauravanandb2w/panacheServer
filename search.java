
package panacheServer;

/**
 *
 * @author saurav anand
 */
import java.sql.*;
import java.util.*; 



class Pair { 
    int x; 
    int y; 
  
    // Constructor 
public Pair(int x, int y) 
    { 
        this.x = x; 
        this.y = y; 
    } 
}




class Compare { 
  
    static void compare(Pair arr[], int n) 
    { 
        //System.out.println("inside compare");
        // Comparator to sort the pair according to second element 
        Arrays.sort(arr, new Comparator<Pair>() { 
            public int compare(Pair p1, Pair p2) 
            { 
                //System.out.println("inside compare1");
                return p2.y - p1.y; 
            } 
        }); 
    } 
}



public class search {
    Connection con;
    
    Pair []count=new Pair[100];
    String [][]data=new String[100][2];
    String ans;
    String tosearch;
    int len=0;
    
    search(String str)
    {
        this.tosearch=str;
    }
    
    public void connect()
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/server","root","mydatabase#4178");
            System.out.println("connected!!");
        }
        catch(SQLException e)
        {
            System.out.println(" Unable to connect the databese "+e.getMessage());
        }
    }
    
    public void fetch()
    {
        
        try 
        {
            String sql = "select path,tags from video;";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs;
            rs = pst.executeQuery();
            while(rs.next())
            {
                data[this.len][0]=rs.getString("path");
                data[this.len][1]=rs.getString("tags");
                this.len++;
            }
            System.out.println("VALUE FETCHED!!");
        }
        catch (SQLException ex) 
        {
            System.out.println("Error in getting and updating count ");
            System.out.println(ex.getMessage());
        }
    }
 /////////////////////   
    int max(int a, int b) 
    { 
        return (a > b)? a : b; 
    } 
   
    int lcs( char[] X, char[] Y, int m, int n ) 
    {
        { 
            if (m == 0 || n == 0) 
                return 0; 
            if (X[m-1] == Y[n-1]) 
                return 1 + lcs(X, Y, m-1, n-1); 
            else
                return max(lcs(X, Y, m, n-1), lcs(X, Y, m-1, n)); 
        }
    }    
//////////////////////  
    
    public void fillcount()
    {
        //System.out.println(this.len+" ");
        for(int j=0;j<this.len;j++)
        {
            char[] X=this.tosearch.toCharArray(); 
            char[] Y=data[j][1].toCharArray(); 
            int m = X.length; 
            int n = Y.length;
            this.count[j]=new Pair(j,this.lcs(X,Y,m,n));
            
        }
        //for(int j=0;j<this.len;j++)
            //System.out.println(" "+count[j].y);
    }                                                                           //OK
  
    
    public String searches()
    {
        int i,p=0;
        String no;
        no="NO ENTRIES FOUND!!";
        //for(int j=0;j<this.len;j++)
            System.out.println(" "+len);
            try{
                Compare ob = new Compare();    
                ob.compare(count,len); 
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        for(i=0;i<len;i++)
        {
            //System.out.println(i+" ");
            if(count[i].y==0)
                    break;
            ans+="#";
            ans+=data[count[i].x][0];
            p++;
            if(p==5)
                   break;
        }
        
        if(i==0)
            return no;
        else
            return ans;
    }

    public static void main(String []args)
    {
        //String []output = new String[5];
        String output;
        search ob = new search("uriuri");
        ob.connect();                          //connected to database.
        ob.fetch();                            //fetching path and tags from table videos.
        ob.fillcount();                        //filling pair array and sorting the array according to number of matches.(in descending order). 
        output = ob.searches();
            System.out.println(output);
        
    }
}


    
    
   
    

