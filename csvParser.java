import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class csvParser
{
  private int fooCount=0;
  private int barCount=0;
  private int foobarCount=0;
  private int fooColumn=-1;
  private int barColumn=-1;
  private boolean header=false;

  private void processFile(String fileName)
  {
    try
    {
      File csvFile = new File(fileName);
      Scanner csvReader = new Scanner(csvFile);
      while (csvReader.hasNextLine())
      {
        processLine(csvReader.nextLine());
      }
      csvReader.close();
      printResult();
    }
    catch (FileNotFoundException e)
    {
      System.err.println("Error: File not found [" + fileName + "]");
    }
  }

  private void processLine(String line)
  {
    if (!header)
    {
      // Process header
      String[] cols = line.split(",");
      int i = 0;
      for (String col : cols)
      {
        if (col.equals("foo"))
          fooColumn = i;
        else if (col.equals("bar"))
          barColumn = i;
        i++;
      }
      if (fooColumn < 0 || barColumn < 0)
      {
        System.err.println("Error: Missing column(s) in input file");
      }
      else
        header=true;
    }
    else // Process Data
    {
        String[] cols = line.split(",");
        if (cols[fooColumn].equals("1") && cols[barColumn].equals("1"))
          foobarCount++;
        else if (cols[fooColumn].equals("1"))
          fooCount++;
        else if (cols[barColumn].equals("1"))
          barCount++;
    }
  }

  private void printResult()
  {
    System.out.println("foo: " + fooCount);
    System.out.println("bar: " + barCount);
    System.out.println("foobar: " + foobarCount);
  }

  public static void main(String[] args)
  {
    if (args.length != 1)
    {
      // print usage help
      System.out.println("Usage: java csvParser <csv file name>");
      return;
    }
    csvParser parser = new csvParser();
    parser.processFile(args[0]);
  }
}
