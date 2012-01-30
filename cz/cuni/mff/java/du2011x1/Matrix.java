package cz.cuni.mff.java.du2011x1;


public class Matrix {
	
	public int SizeX, SizeY;
	public Double[][] Value;

	public Matrix(String s, String Ypattern, String Xpattern) {
		String[] sn = s.split(Ypattern);
		
		
		String[][] sMatrix = new String[sn.length][];
		for (int i = 0; i < sn.length; i++) {
			sMatrix[i] = sn[i].split(Xpattern);
		}
		this.SizeX = sMatrix[0].length;
		this.SizeY = sn.length;
		this.Value = new Double[this.SizeY][this.SizeX];
		
		for (int i = 0; i < this.SizeY; i++) {
			for (int j = 0; j < this.SizeX; j++) {
				this.Value[i][j] = Double.parseDouble(sMatrix[i][j]);
			}
		}
		
	}
	
	public Matrix(int x, int y)
	{
		this.SizeX = x;
		this.SizeY = y;
		this.Value = new Double[this.SizeX][this.SizeY];
	}

	public void Print() {
		for (int i = 0; i < this.SizeY; i++) {
			for (int j = 0; j < this.SizeX; j++) {
				System.out.printf("%.5f ", this.Value[i][j]);
			}
			System.out.println();
		}
	}

	public static Matrix MultiplyByScalar(Double d, Matrix A) {
		Matrix result = new Matrix(A.SizeX, A.SizeY);
		for (int j = 0; j < A.SizeY; j++) {
			for (int i = 0; i < A.SizeX; i++) {
				result.Value[i][j] = d * A.Value[i][j];
			}
		}
		return result;
	}

	public static Matrix ParticularSubtract(Double d, Matrix A) {
		Matrix result = new Matrix(A.SizeX, A.SizeY);
		for (int j = 0; j < A.SizeY; j++) {
			for (int i = 0; i < A.SizeX; i++) {
				result.Value[i][j] = d - A.Value[i][j];
			}
		}
		return result;
	}

	public static Matrix ParticularSubtract(Matrix A, Double B) {
		Matrix result = new Matrix(A.SizeX, A.SizeY);
		for (int j = 0; j < A.SizeY; j++) {
			for (int i = 0; i < A.SizeX; i++) {
				result.Value[i][j] = A.Value[i][j] - B;
			}
		}
		return result;
	}

	public static Matrix ParticularDivide(Matrix A, Matrix B) throws Exception {
		if (A.SizeX!=B.SizeX || A.SizeY != B.SizeY)
		{
			Exception e = new Exception();
			throw e;
		}
		Matrix result = new Matrix(A.SizeX, A.SizeY);
		for (int j = 0; j < A.SizeY; j++) {
			for (int i = 0; i < A.SizeX; i++) {
				result.Value[i][j] = A.Value[i][j] / B.Value[i][j];
			}
		}
		return result;
	}

	public static Matrix ParticularMultiply(Matrix A, Matrix B) throws Exception {
		if (A.SizeX!=B.SizeX || A.SizeY != B.SizeY)
		{
			Exception e = new Exception();
			throw e;
		}
		Matrix result = new Matrix(A.SizeX, A.SizeY);
		for (int j = 0; j < A.SizeY; j++) {
			for (int i = 0; i < A.SizeX; i++) {
				result.Value[i][j] = A.Value[i][j] * B.Value[i][j];
			}
		}
		return result;
	}

	public static Matrix Divide(Matrix A, Matrix B) throws Exception {
		Matrix semiResult = B.ComputeInverse();
		return Matrix.Multiply(A, semiResult);
	}

	public static Matrix Multiply(Matrix A, Matrix B) {
		Matrix Result = new Matrix(B.SizeX, A.SizeY);
		for (int i = 0; i < Result.SizeX; i++) {
			for (int j = 0; j < Result.SizeY; j++) {
				Result.Value[j][i] = 0.0d;
				for (int k = 0; k < A.SizeX; k++) {
					Result.Value[j][i] += A.Value[j][k] * B.Value[k][i];
				}
			}
		}
		return Result;
	}

	public static Matrix Subtract(Matrix A, Matrix B) throws Exception {
		if (A.SizeX!=B.SizeX || A.SizeY != B.SizeY)
		{
			Exception e = new Exception();
			throw e;
		}
		Matrix result = new Matrix(A.SizeX, A.SizeY);
		for (int j = 0; j < A.SizeY; j++) {
			for (int i = 0; i < A.SizeX; i++) {
				result.Value[i][j] = A.Value[i][j] - B.Value[i][j];
			}
		}
		return result;
	}

	public static Matrix Add(Matrix A, Matrix B) throws Exception {
		if (A.SizeX!=B.SizeX || A.SizeY != B.SizeY)
		{
			Exception e = new Exception();
			throw e;
		}
		Matrix result = new Matrix(A.SizeX, A.SizeY);
		for (int j = 0; j < A.SizeY; j++) {
			for (int i = 0; i < A.SizeX; i++) {
				result.Value[i][j] = A.Value[i][j] + B.Value[i][j];
			}
		}
		return result;
	}
	
	private Matrix ComputeInverse() throws Exception
	{
		int order = this.SizeX;
		Double[][] A = new Double[this.SizeX][this.SizeY];
		Matrix Result = new Matrix(this.SizeX, this.SizeY);
		for (int i = 0; i < this.SizeX; i++) {
			for (int j = 0; j < this.SizeY; j++) {
				A[i][j] = this.Value[i][j];
			}
		}
	    Double det = 1.0/Determinant(A,order);
	    if (det == Double.NEGATIVE_INFINITY || det == Double.POSITIVE_INFINITY)
	    {
	    	Exception e = new Exception();
	    	throw e;
	    }
	    Double [][]minor = new Double[order-1][];
	    for(int i=0;i<order-1;i++)
	        minor[i] = new Double[(order-1)];

	    for(int j=0;j<order;j++)
	    {
	        for(int i=0;i<order;i++)
	        {
	            Minor(A,minor,j,i,order);
	            Result.Value[i][j] = det * Determinant(minor,order-1);
	            if( (i+j)%2 == 1)
	            	Result.Value[i][j] = -Result.Value[i][j];
	        }
	    }
	    return Result;
	}

	private int Minor(Double [][]src, Double [][]dest, int row, int col, int order)
	{
	    int colCount=0,rowCount=0;

	    for(int i = 0; i < order; i++ )
	    {
	        if( i != row )
	        {
	            colCount = 0;
	            for(int j = 0; j < order; j++ )
	            {
	                if( j != col )
	                {
	                    dest[rowCount][colCount] = src[i][j];
	                    colCount++;
	                }
	            }
	            rowCount++;
	        }
	    }
	    return 1;
	}

	private Double Determinant( Double[][] mat, int order)
	{
	    if( order == 1 )
	        return mat[0][0];
	    Double det = 0.0d;
	    Double minor[][];
	    minor = new Double[order-1][];

	    for(int i=0;i<order-1;i++)
	        minor[i] = new Double[order-1];

	    for(int i = 0; i < order; i++ )
	    {
	        Minor( mat, minor, 0, i , order);
	        det += (i%2==1?-1.0:1.0) * mat[0][i] * Determinant(minor,order-1);
	    }
	    return det;
	}
	
}
