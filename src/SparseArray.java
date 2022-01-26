import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class SparseArray
{
	public static void main(String[] args)
	{
		int[][] original = new int[11][11];
		original[1][2] = 1;
		original[2][3] = 2;

		// encode to sparse array
		int[][] sparseArr = toSparseArray(original);

		// serialise sparse array to data file
		try
		{
			String dest = "sparseArr.dat"; // under root
			serialise(sparseArr , dest);
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}

		// deserialise sparse array from data file
		int[][] deserialisedSparseArr = null;
		try
		{
			String path = "sparseArr.dat";
			deserialisedSparseArr = deserialise(path);
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		/*
		11	11	2
		1	2	1
		2	3	2
		*/

		// decode to original array
		int[][] decode = toOriginal(deserialisedSparseArr);
	}

	public static int[][] toSparseArray(int[][] original)
	{
		// traverse for # of valid values
		int sum = 0;

		for (int[] row : original)
		{
			for (int val : row)
			{
				if (val != 0) sum++;
			}
		}

		// create corresponding sparse array
		int[][] sparseArr = new int[sum + 1][3];

		// assign info to top row: row, col, val#
		sparseArr[0][0] = original.length;
		sparseArr[0][1] = original[0].length;
		sparseArr[0][2] = sum;

		// traverse again to assign sparse array
		int count = 1; // counter, used to specify row in $sparseArr
		for (int row = 0 ; row < original.length ; row++)
		{
			for (int col = 0 ; col < original[row].length ; col++)
			{
				int val = original[row][col];

				if (val != 0)
				{
					sparseArr[count][0] = row;
					sparseArr[count][1] = col;
					sparseArr[count][2] = val;
					count++;
				}

				if (count == sum + 1) break;
				// last row reached -> all valid data assigned, no need to continue
			}
		}

		return sparseArr;
	}

	public static int[][] toOriginal(int[][] sparse)
	{
		// read top row
		int rows = sparse[0][0];
		int cols = sparse[0][1];

		// create
		int[][] original = new int[rows][cols];

		// assign
		for (int i = 1 ; i < sparse.length ; i++)
		{
			int[] line = sparse[i];

			int row = line[0];
			int col = line[1];
			int val = line[2];

			original[row][col] = val;
		}

		return original;
	}

	public static void serialise(int[][] arr , String path) throws IOException, FileNotFoundException
	{
		FileOutputStream fout = new FileOutputStream(path);
		ObjectOutputStream oout = new ObjectOutputStream(fout);

		oout.writeObject(arr);

		fout.close();
		oout.close();
	}

	public static int[][] deserialise(String path) throws IOException, FileNotFoundException, ClassNotFoundException
	{
		FileInputStream fin = new FileInputStream(path);
		ObjectInputStream oin = new ObjectInputStream(fin);

		Object res = oin.readObject();

		fin.close();
		oin.close();

		return (int[][])res;
	}
}
