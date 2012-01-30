package cz.cuni.mff.java.du2011x1;


public class EquationReader {
	
	enum Side
	{
		Left,
		Right
	}
	
	enum Operators
	{
		Plus,
		Minus,
		Over,
		Times,
		EscapedOver,
		EscapedTimes
	}
	
	public EquationReader Left;
	public EquationReader Right;
	public Object Value;
	public Side Operand;
	public Operators Operator; 
	
	public void Evaluate() throws Exception 
	{
		if (this.Left == null && this.Right == null)
		{
			return;
		}
		else if (this.Left == null ^ this.Right == null)
		{
			this.Value = (this.Left == null) ? this.Right.Value : this.Left.Value;
			return;
		}
		
		
		else if (this.Right.Value instanceof Double && this.Left.Value instanceof Double)
		{
			switch (this.Operator) {
			case Plus:
				this.Value = (Double)this.Left.Value + (Double)this.Right.Value;
				break;
			case Minus:
				this.Value = (Double)this.Left.Value - (Double)this.Right.Value;
				break;
			case Times:
				this.Value = (Double)this.Left.Value * (Double)this.Right.Value;
				break;
			case Over:
				this.Value = (Double)this.Left.Value / (Double)this.Right.Value;
				break;
			case EscapedTimes:
				this.Value = (Double)this.Left.Value * (Double)this.Right.Value;
				break;
			case EscapedOver:
				this.Value = (Double)this.Left.Value / (Double)this.Right.Value;
				break;

			default:
				break;
			}
		}
		else if (this.Right.Value instanceof Matrix && this.Left.Value instanceof Matrix)
		{
			switch (this.Operator) {
			case Plus:
				this.Value = Matrix.Add((Matrix)this.Left.Value, (Matrix)this.Right.Value);
				break;
			case Minus:
				this.Value = Matrix.Subtract((Matrix)this.Left.Value, (Matrix)this.Right.Value);
				break;
			case Times:
				this.Value = Matrix.Multiply((Matrix)this.Left.Value, (Matrix)this.Right.Value);
				break;
			case Over:
				this.Value = Matrix.Divide((Matrix)this.Left.Value, (Matrix)this.Right.Value);
				break;
			case EscapedTimes:
				this.Value = Matrix.ParticularMultiply((Matrix)this.Left.Value, (Matrix)this.Right.Value);
				break;
			case EscapedOver:
				this.Value = Matrix.ParticularDivide((Matrix)this.Left.Value, (Matrix)this.Right.Value);
				break;

			default:
				break;
			}
		}
		else
		{
			if (this.Left.Value instanceof Matrix && this.Right.Value instanceof Double)
			{
				switch (this.Operator) {
				case Plus:
					this.Value = Matrix.ParticularSubtract((Matrix)this.Left.Value, -1.0d * (Double)this.Right.Value);
					break;
				case Minus:
					this.Value = Matrix.ParticularSubtract((Matrix)this.Left.Value, (Double)this.Right.Value);
					break;
				case Times:
					this.Value = Matrix.MultiplyByScalar((Double)this.Right.Value, (Matrix)this.Left.Value);
					break;
				case Over:
					this.Value = Matrix.MultiplyByScalar(1.0d / (Double)this.Right.Value, (Matrix)this.Left.Value);
					break;
				case EscapedTimes:
					this.Value = Matrix.MultiplyByScalar((Double)this.Right.Value, (Matrix)this.Left.Value);
					break;
				case EscapedOver:
					this.Value = Matrix.MultiplyByScalar(1.0d / (Double)this.Right.Value, (Matrix)this.Left.Value);
					break;
				}
			}
			if (this.Left.Value instanceof Double && this.Right.Value instanceof Matrix)
			{
			
				switch (this.Operator) {
				case Plus:
					this.Value = Matrix.ParticularSubtract( (Matrix)this.Right.Value, -1.0d * (Double)this.Left.Value);
					break;
				case Minus:
					this.Value = Matrix.ParticularSubtract((Double)this.Left.Value, (Matrix)this.Right.Value);
					break;
				case Times:
					this.Value = Matrix.MultiplyByScalar((Double)this.Left.Value, (Matrix)this.Right.Value);
					break;
				case Over:
					this.Value = Matrix.MultiplyByScalar(1.0d / (Double)this.Left.Value, (Matrix)this.Right.Value);
					break;
				case EscapedTimes:
					this.Value = Matrix.MultiplyByScalar((Double)this.Left.Value, (Matrix)this.Right.Value);
					break;
				case EscapedOver:
					this.Value = Matrix.MultiplyByScalar(1.0d / (Double)this.Left.Value, (Matrix)this.Right.Value);
					break;
				}
			}
		}
	}
	
	
	public Matrix ReadMatrix(char c) throws Exception
	{
		Matrix result = null;
		String s = "";
		int i = 0;
		Boolean number = false;
		Boolean newRow = true;
		do 
		{
			if ( c == '\n' || c == '\r') c = ';';
			if (c == ';')
			{
				s += ";";
				number = false;
				newRow = true;
			}
			if (number)
			{
				if (c== '-' || c == 'e' || (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9))
				{
					s += Character.toString(c);
				}
				if (c == ' ')
				{
					number = false;
				}
			}
			else
			{
				if (c== '-' || c == 'e' || (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9))
				{
					if (!newRow)
						s += ",";
					s += Character.toString(c);
					number = true;
					newRow = false;
				}
			}
			
			c = (char)i;
			if (c == ']')
			{
				result = new Matrix(s, ";", ",");
				break;
			}
		} 
		//while ((i = isr.read()) != -1);
		while ((char)(i = System.in.read()) != -1);
		return result;
	}
	
	public char Read(Boolean Temporary) throws Exception
	{
		this.Operand = (Temporary)? Side.Right : Side.Left;
		
		Boolean expectedOperand = true;
		
		int i = 0;
		char c = ' ';
		String s = "";
		Boolean escapeCharacter = false;
		while ( (char)(i = System.in.read()) != -1)
		//while ( (i = isr.read()) != -1)
		{
			c = (char)i; 
			if (c == '\n' || c == '\r')
				break;
			if (Temporary)
			{
				if (s != "" && !( c == 'e' || (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9)))
				{
					this.Right = new EquationReader();
					this.Right.Value = Double.parseDouble(s);
					this.Evaluate();
					return c;
				}
			}
			
			if (c == '.')
			{
				escapeCharacter = true;
				continue;
			}
			else if (c == '[')
			{
				Matrix m = ReadMatrix( c);
				if (this.Operand == Side.Left)
				{
					this.Left = new EquationReader();
					this.Left.Value = m;
				}
				else
				{
					this.Right = new EquationReader();
					this.Right.Value = m;
				}
				expectedOperand = false;
			}
			else if ( expectedOperand && ( c == '-' || (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9)))
			{
				  s += Character.toString(c);
				  expectedOperand = false;
			}
			else if (!expectedOperand && ( c == 'e' || (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9)))
			{
				if (c == 'e')
				{
					s += Character.toString(c);
					expectedOperand = true;
					continue;
				}
				s += Character.toString(c);
			}
			else if (!expectedOperand && (c == '*' || c == '/' || c == '+' || c == '-' ))
			{
				expectedOperand = true;
				if (s != "")
				{
					setUpOperands(s);
					s = "";
				}
				
				if (this.Operand == Side.Left)
				{
					this.Operand = Side.Right;
				}
				else
				{
					if (	   c == '+' || c == '-'
							|| this.Operator == Operators.Times 
							|| this.Operator == Operators.Over 
							|| this.Operator == Operators.EscapedOver 
							|| this.Operator == Operators.EscapedTimes)
					{
						this.Evaluate();
						this.Left.Value = this.Value;
						
						switch (c) {
						case '*':
							this.Operator = (escapeCharacter) ? Operators.EscapedTimes : Operators.Times;
							break;
							
						case '/':
							this.Operator = (escapeCharacter) ? Operators.EscapedOver : Operators.Over;
							break;
							
						case '+':
							this.Operator = Operators.Plus;
							break;
							
						case '-':
							this.Operator = Operators.Minus;
							break;
							
						default:
							break;
						}
						
						
						continue;
					}
					else
					{
						EquationReader temp = this.Right;
						this.Right = new EquationReader();
						this.Right.Left = temp;
						this.Right.Operand = Side.Right;
						
						switch (c) {
						case '*':
							this.Right.Operator = (escapeCharacter) ? Operators.EscapedTimes : Operators.Times;
							break;
							
						case '/':
							this.Right.Operator = (escapeCharacter) ? Operators.EscapedOver : Operators.Over;
							break;
							
						case '+':
							this.Right.Operator = Operators.Plus;
							break;
							
						case '-':
							this.Right.Operator = Operators.Minus;
							break;
							
						default:
							break;
						}
						this.Right.Operand = Side.Right;
						c = this.Right.Read(true);
						expectedOperand = false;
						this.Right.Evaluate();
						this.Evaluate();
						this.Left.Value = this.Value;
						this.Operand = Side.Left;
						this.Right = null;
						continue;
					}
				}
				switch (c) {
				case '*':
					this.Operator = (escapeCharacter) ? Operators.EscapedTimes : Operators.Times;
					break;
					
				case '/':
					this.Operator = (escapeCharacter) ? Operators.EscapedOver : Operators.Over;
					break;
					
				case '+':
					this.Operator = Operators.Plus;
					break;
					
				case '-':
					this.Operator = Operators.Minus;
					break;
					
				default:
					break;
				}
			}
			else if (c == '(')
			{
				if (this.Operand == Side.Left)
				{
					this.Left = new EquationReader();
					this.Left.Read( false);
					expectedOperand = false;
				}
				else
				{
					this.Right = new EquationReader();
					this.Right.Read( false);
					expectedOperand = false;
				}
			}
			else if (c == ' ')
			{
				if (s != "")
				{
					setUpOperands(s);
					s = "";
				}
			}
			else if ( c == ')')
			{
				if (s != "")
				{
					setUpOperands(s);
					s = "";
				}
				this.Evaluate();
				return c;
			}
			else if ( !Character.isWhitespace(c))
			{
				Exception e = new Exception();
				throw e;
			}
			
			escapeCharacter = false;
		}
		
		/*if left some numbers in s*/
		if (s != "")
		{
			setUpOperands(s);
			s = "";
		}
			this.Evaluate();
		
		return ' ';
	}
	
	private void setUpOperands(String s)
	{
		if (this.Operand == Side.Left)
		{
			if (this.Left == null) this.Left = new EquationReader();
			if (s.contains("e"))
			{
				String[] subNumber = s.split("e");
				Double SemiResult = Double.parseDouble(subNumber[0]) * Math.pow(10, Double.parseDouble(subNumber[1]));
				this.Left.Value = (SemiResult);
			}
			else
				this.Left.Value = Double.parseDouble(s);;
			s = "";
		}
		else if (this.Operand == Side.Right)
		{
			if (this.Right == null) this.Right = new EquationReader();
			if (s.contains("e"))
			{
				String[] subNumber = s.split("e");
				Double SemiResult = Double.parseDouble(subNumber[0]) * Math.pow(10, Double.parseDouble(subNumber[1]));
				this.Right.Value = (SemiResult);
			}
			else
				this.Right.Value = Double.parseDouble(s);
			this.Right.Value = Double.parseDouble(s);;
			s = "";	
		}
	}
}
