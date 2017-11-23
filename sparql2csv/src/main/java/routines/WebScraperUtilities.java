package routines;

import java.util.ArrayList;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.visitors.NodeVisitor;
import org.htmlparser.Tag;
import org.htmlparser.tags.TableTag;

/*
 * user specification: the function's comment should contain keys as follows: 1. write about the function's comment.but
 * it must be before the "{talendTypes}" key.
 * 
 * 2. {talendTypes} 's value must be talend Type, it is required . its value should be one of: String, char | Character,
 * long | Long, int | Integer, boolean | Boolean, byte | Byte, Date, double | Double, float | Float, Object, short |
 * Short
 * 
 * 3. {Category} define a category for the Function. it is required. its value is user-defined .
 * 
 * 4. {param} 's format is: {param} <type>[(<default value or closed list values>)] <name>[ : <comment>]
 * 
 * <type> 's value should be one of: string, int, list, double, object, boolean, long, char, date. <name>'s value is the
 * Function's parameter name. the {param} is optional. so if you the Function without the parameters. the {param} don't
 * added. you can have many parameters for the Function.
 * 
 * 5. {example} gives a example for the Function. it is optional.
 */
public class WebScraperUtilities {

    /**
     * parseF1Table: a method to process <table> data on the http://www.statsf1.com/en/2014.aspx website.
     * This method can easily be extended to be used for other sites.
     * 
     * 
     * {talendTypes} ArrayList
     * 
     * {Category} User Defined
     * 
     * {param} String("html") input: A string representation of the website
     * 
     */
	public static ArrayList<ArrayList<String>> parseF1Table(String html){

		//Define an ArrayList to hold the data found by the NodeVisitor class
		final ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
	   
		//if the html string is not null
		if(html!=null){
	    
			//Instantiate an instance of the NodeVisitor class and override its visitTag method
			final NodeVisitor linkVisitor = new NodeVisitor() {
				    
		        @Override
		        public void visitTag(Tag tag) {
		        	
		        	//If the tag is an instance of the TableTag, process the data
		        	if(tag instanceof TableTag ){
		        		TableTag tt = (TableTag)tag;
	        			TableRow[] trows = tt.getRows();
		        
	        			//For each row, get the column data
	        			for(int i = 0; i<trows.length; i++){
		        			TableRow tr = trows[i];
		        			TableColumn[] tcols = tr.getColumns();
		        			ArrayList<String> tmpRow = new ArrayList<String>();
		       
		        			//If the column length is 21, then we know this column is a header
		        			if(tcols.length==21){
		        				tmpRow.add("");
		        			}
		        			
		        			//if this row has more than 1 column
		        			if(tcols.length>1){
		        				//Get each column value
		        				for(int x = 0; x<tcols.length; x++){
		        					TableColumn tc = tcols[x];
		        					String columnVal = tc.toPlainTextString().trim();
		        					
		        					//Remove "&nbsp;" strings from the column values
		        					columnVal = columnVal.replaceAll("&nbsp;", "");
		        					tmpRow.add(columnVal);
		        				}
		        				rows.add(tmpRow);
		        			}
		        		}
		        	}
		        }
		    };

		    //Instantiate an instance of the Parse class
		    Parser parser = Parser.createParser(html, null);
		    //Instantiate an empty instance of the NodeList class
		    NodeList list;
		    
		    try {
		    	//Assign the NodeList instance (list) an object
		        list = parser.parse(null);
		        //Assign the list object a Visitor ....defined above.
		        list.visitAllNodesWith(linkVisitor);
		        return rows;//list.toHtml();
		    } catch (ParserException e) {
		        // Could not parse HTML, return original HTML
		        return null;
		    }
		}else{
			return rows;
	    }
		
	}

}
