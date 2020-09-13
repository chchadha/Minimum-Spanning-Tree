package apps;
import java.io.IOException;
import java.util.*;

import structures.Graph;

public class Driver
{
    public static void main(String args[]) throws Exception
    {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Enter text name:");
        String file = stdin.next();

        Graph s1 = new Graph(file);
        //

        PartialTreeList pt2 = MST.initialize(s1);



        Iterator<PartialTree> itr = pt2.iterator();
        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }

        System.out.println("pt1=pt2.remove()");
        PartialTree pt1 = pt2.remove();

        //Iterator<PartialTree>
        itr = pt2.iterator();
        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }

	/*	//In case I want to loop over something again
		itr=pt2.iterator();
	*/

    }
}