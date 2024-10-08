package MatrixLibrary;

import java.util.Arrays;

// This shall be the Java matrix library 
// *YIPPEE*


/*
 * 
 * I think it might be worth mentioning the layout of the data
 * 
 * data = {
 * {blah, blah, blah}, 
 * {blah, blah, blah},
 * {blah, blah, blah}
 * }
 * 
 * This is kind of weird because some places might visualize it differently(I would add an example but I don't want to make it 
 * any more confusing) so just know that to reference things from the data in the data array from the Matrix class you need to use
 * 
 * matrixObject.data[row][column]
 * 
 * Have a great day!
 * 
 */

public class Matrix {

    public static void main(String[] args) {
        
        double[][] data1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        double[] data2 = {
            1, 2, 3, 4, 5
        };


        Matrix mat = new Matrix(data2);
        Matrix mat1 = new Matrix(mat.toVector());
        mat1.print();
        // activationFunction sigmoid = x -> 1 / (1 + Math.exp(-x));
        // mat.map(sigmoid).print();
    }

    // PIVs ------------------------------------------

    // The dimensions of the matrix
    private int rows;
    private int cols;

    // The data in the matrix
    private double[][] data;


    // The random constructor
    public Matrix(int numRows, int numCols) {

        // Taking down the dimensions
        this.rows = numRows;
        this.cols = numCols;

        // We just want a random matrix with this constructor, so here is where we can randomize it
        this.data = new double[this.rows][this.cols];

        // Looping for every value in the matrix
        for (int rowNum = 0; rowNum < this.rows; rowNum++) {

            for (int colNum = 0; colNum < this.cols; colNum++) {

                this.data[rowNum][colNum] = Math.random();

            }

        }

    }

    // The non random constructor
    public Matrix(double[][] inputData) {

        // Setting the dimensions
        this.rows = inputData.length;
        this.cols = inputData[0].length;

        // Declaring the data variable
        this.data = new double[this.rows][this.cols];

        // Running a deep copy so that we can make sure there aren't any reference errors
        for (int i = 0; i < this.rows; i++) {

            this.data[i] = Arrays.copyOf(inputData[i], inputData[i].length);

        }

    }

    // Another non random constructor for vectors
    public Matrix(double[] inputData) {

        // Setting the dimensions
        this.rows = inputData.length;
        this.cols = 1;

        // Declaring the data variable
        this.data = new double[this.rows][this.cols];

        // Running a deep copy so that we can make sure there aren't any reference errors
        for (int i = 0; i < this.rows; i++) {

            this.data[i][0] = inputData[i];

        }

    }

    // The copy constructor
    public Matrix(Matrix toCopy) {

        // All we really need to do is just the same thing as in another constructor
        // Having two just makes it easier for the end user

        // Setting the dimensions
        this.rows = toCopy.data.length;
        this.cols = toCopy.data[0].length;

        // Declaring the data variable
        this.data = new double[this.rows][this.cols];

        // Running a deep copy so that we can make sure there aren't any reference errors
        for (int i = 0; i < this.rows; i++) {

            this.data[i] = Arrays.copyOf(toCopy.data[i], toCopy.data[i].length);

        }

    }

    // The empty constructor
    public Matrix() {
        this.cols = 0;
        this.rows = 0;
        this.data = null;
    }

    // Now for the methods ---------------------------------------------------------

    // A simple print function (not too fancy unfortunately)
    public Matrix print() {

        // The maximum number of digits
        final int MAX_DIGITS = 5;

        // Looping for every value in the matrix
        for (int rowNum = 0; rowNum < this.rows; rowNum++) {

            for (int colNum = 0; colNum < this.cols; colNum++) {

                // We want to limit the string length so that we can make sure that all of the squares are nice and pretty
                String str = "" + this.data[rowNum][colNum];

                // If it is already bigger than the max length, we can truncate it and be done
                if (str.length() > MAX_DIGITS) {
                    str = str.substring(0, 5);
                } else {

                    // It is smaller so we need to append spaces to the end to make it the same size
                    while (str.length() < MAX_DIGITS) {
                        str = " " + str;
                    }

                }

                // Adding on the style :D
                if (colNum == 0) {
                    str = "| " + str;
                     if (colNum == this.cols - 1) {
                        str += " |";
                     } else {
                        str += ", ";
                     }
                } else if (colNum == this.cols - 1) {
                    str += " |";
                } else {
                    str += ", ";
                }
                

                // Printing out this element
                System.out.print(str);

            }

            // Adding on the carriage return
            System.out.println();

        }

        // Adding on another carriage return so that we can print a bunch in a row
        System.out.println();

        // Return self 
        return this;

    }

    //--------------------------------------------------Multiplication---------------------------------------------------------------

    // The scalar element multiplication method
    public Matrix elementMult(double scalar) {

        // This specific element multiplication method is going to be the scalar one, but there will also be 
        // one for Matrices that are the same size 

        // Looping for every value in the matrix to multiply by the scalar
        for (int rowNum = 0; rowNum < this.rows; rowNum++) {

            for (int colNum = 0; colNum < this.cols; colNum++) {

                this.data[rowNum][colNum] *= scalar;

            }

        }

        // This is not a static method, so it changes itself and returns self
        return this;

    }

    // The static scalar element multiplication method 
    public static Matrix elementMult(Matrix mat, double scalar) {

        // The new matrix that we will be returning 
        Matrix newMat = new Matrix();
        newMat.rows = mat.rows;
        newMat.cols = mat.cols;
        newMat.data = new double[mat.rows][mat.cols];

        // Looping for every value in the matrix to multiply by the scalar
        for (int rowNum = 0; rowNum < mat.rows; rowNum++) {

            for (int colNum = 0; colNum < mat.cols; colNum++) {

                newMat.data[rowNum][colNum] = mat.data[rowNum][colNum] * scalar;

            }

        }

        // This is a static method so no changing self
        return newMat;

    }

    // The element wise multiplication method 
    public Matrix elementMult(Matrix other) {

        // Making sure that they have the same dimensions
        if (this.rows != other.rows || this.cols != other.cols) {

            // Telling them a bit about the error that just occurred
            System.out.println("In element-wise multiplication, both matrices have to have the same dimensions.");

            // Printing out the matrices so that they know a bit more about what went wrong
            System.out.println("Matrix A:");
            this.print();
            System.out.println("\nMatrix B:");
            other.print();

            // Throwing an error to stop the program
            throw new Error("ElementMultiplicationSizeError");

        }

        // Looping for every value in the matrix to multiply by the scalar
        for (int rowNum = 0; rowNum < this.rows; rowNum++) {

            for (int colNum = 0; colNum < this.cols; colNum++) {

                this.data[rowNum][colNum] *= other.data[rowNum][colNum];

            }

        }

        // This is not a static method, so it changes itself and returns self
        return this;

    }

    // The static element wise multiplication method
    public static Matrix elementMult(Matrix one, Matrix two) {

        // Making sure that they have the same dimensions
        if (one.rows != two.rows || one.cols != two.cols) {

            // Telling them a bit about the error that just occurred
            System.out.println("In element-wise multiplication, both matrices have to have the same dimensions.");

            // Printing out the matrices so that they know a bit more about what went wrong
            System.out.println("Matrix A:");
            one.print();
            System.out.println("\nMatrix B:");
            two.print();

            // Throwing an error to stop the program
            throw new Error("StaticElementMultiplicationSizeError");

        }

        // This is a static method so we need a new Matrix to return 
        Matrix newMat = new Matrix(one);

        // Looping for every value in the matrix to multiply by the scalar
        for (int rowNum = 0; rowNum < one.rows; rowNum++) {

            for (int colNum = 0; colNum < one.cols; colNum++) {

                newMat.data[rowNum][colNum] *= two.data[rowNum][colNum];

            }

        }

        // This is not a static method, so it changes itself and returns self
        return newMat;

    }

    // The matrix multiplication method
    public Matrix matrixMult(Matrix other) {

        // First thing we have to do is check the sizes
        if (other.rows != this.cols) {

            System.out.println("The number of columns in the first matrix must match the number of rows in the second matrix in matrix multiplication.");

            // Giving them a bit of data
            System.out.println("Matrix A:");
            this.print();
            System.out.println("\nMatrix B:");
            other.print();

            // Throwing an error to stop their program
            throw new Error("MatrixMultiplicationSizeError");

        }

        // We need to actually make a new matrix to return because it will be a different size
        // We are doing this a strange way to avoid an unneeded loop in the constructor that would randomize it
        Matrix newMat = new Matrix();
        newMat.rows = this.rows;
        newMat.cols = other.cols;
        newMat.data = new double[this.rows][other.cols];

        // Now for the math
        // Looping for every value in the matrix 
        for (int rowNum = 0; rowNum < newMat.rows; rowNum++) {

            for (int colNum = 0; colNum < newMat.cols; colNum++) {

                // Now that we will be at every spot in the Matrix, we need to find the multiplicative sum 
                // So we can loop through the shared dimension
                double sum = 0.0;
                for (int i = 0; i < this.cols; i++) {

                    sum += this.data[rowNum][i] * other.data[i][colNum];

                }

                // Now we just need to assign this value to the new matrix
                newMat.data[rowNum][colNum] = sum;

            }

        }

        // Reassigning self
        this.data = newMat.data;
        this.rows = newMat.rows;
        this.cols = newMat.cols;

        // Returning what we got 
        return this;

    }

    // The static matrix multiplication method
    public static Matrix matrixMult(Matrix one, Matrix two) {
        
        // First thing we have to do is check the sizes
        if (two.rows != one.cols) {

            System.out.println("The number of columns in the first matrix must match the number of rows in the second matrix in matrix multiplication.");

            // Giving them a bit of data
            System.out.println("Matrix A:");
            one.print();
            System.out.println("\nMatrix B:");
            two.print();

            // Throwing an error to stop their program
            throw new Error("StaticMatrixMultiplicationSizeError");

        }

        // We need to actually make a new matrix to return because it will be a different size
        // We are doing this a strange way to avoid an unneeded loop in the constructor that would randomize it
        Matrix newMat = new Matrix();
        newMat.rows = one.rows;
        newMat.cols = two.cols;
        newMat.data = new double[one.rows][two.cols];

        // Now for the math
        // Looping for every value in the matrix 
        for (int rowNum = 0; rowNum < newMat.rows; rowNum++) {

            for (int colNum = 0; colNum < newMat.cols; colNum++) {

                // Now that we will be at every spot in the Matrix, we need to find the multiplicative sum 
                // So we can loop through the shared dimension
                double sum = 0.0;
                for (int i = 0; i < one.cols; i++) {

                    sum += one.data[rowNum][i] * two.data[i][colNum];

                }

                // Now we just need to assign this value to the new matrix
                newMat.data[rowNum][colNum] = sum;

            }

        }

        // Returning what we got 
        return newMat;

    }

    //--------------------------------------------------Addition---------------------------------------------------------------

    // The scalar addition method
    public Matrix add(double scalar) {

        // This specific element addition method is going to be the scalar one, but there will also be 
        // one for Matrices that are the same size 

        // Looping for every value in the matrix to add by the scalar
        for (int rowNum = 0; rowNum < this.rows; rowNum++) {

            for (int colNum = 0; colNum < this.cols; colNum++) {

                this.data[rowNum][colNum] += scalar;

            }

        }

        // This is not a static method, so it changes itself and returns self
        return this;

    }

    // The static scalar addition method
    public static Matrix add(Matrix mat, double scalar) {

        // This specific element addition method is going to be the scalar one, but there will also be 
        // one for Matrices that are the same size 

        // The Matrix that we are going to return (again the empty matrix in an attempt to skip some calculations)
        Matrix newMat = new Matrix();

        // Setting the dimensions of the new matrix
        newMat.rows = mat.rows;
        newMat.cols = mat.cols;

        // Looping for every value in the matrix to add by the scalar
        for (int rowNum = 0; rowNum < mat.rows; rowNum++) {

            for (int colNum = 0; colNum < mat.cols; colNum++) {

                newMat.data[rowNum][colNum] = mat.data[rowNum][colNum] + scalar;

            }

        }

        // This is a static method so no return self
        return newMat;

    }

    // The matrix addition method
    public Matrix add(Matrix other) {

        // Checking to make sure that they are the same size 
        if (this.rows != other.rows || this.cols != other.cols) {

            System.out.println("The matrices in matrix addition must have the same dimensions.");

            // Giving them a bit of data
            System.out.println("Matrix A:");
            this.print();
            System.out.println("\nMatrix B:");
            other.print();

            // Throwing an error to stop their program
            throw new Error("MatrixAdditionSizeError");

        }

        // Looping for every value in the matrix to add by the corresponding value
        for (int rowNum = 0; rowNum < this.rows; rowNum++) {

            for (int colNum = 0; colNum < this.cols; colNum++) {

                this.data[rowNum][colNum] += other.data[rowNum][colNum];

            }

        }

        // Returning self
        return this;

    }

    // The static matrix addition method
    public static Matrix add(Matrix one, Matrix two) {

        // Checking to make sure that they are the same size 
        if (one.rows != two.rows || one.cols != two.cols) {

            System.out.println("The matrices in matrix addition must have the same dimensions.");

            // Giving them a bit of data
            System.out.println("Matrix A:");
            one.print();
            System.out.println("\nMatrix B:");
            two.print();

            // Throwing an error to stop their program
            throw new Error("StaticMatrixAdditionSizeError");

        }

        // Making a new matrix to return
        Matrix newMat = new Matrix();
        newMat.rows = one.rows;
        newMat.cols = one.cols;
        newMat.data = new double[one.rows][one.cols];

        // Looping for every value in the matrix to add by the corresponding value
        for (int rowNum = 0; rowNum < one.rows; rowNum++) {

            for (int colNum = 0; colNum < one.cols; colNum++) {

                newMat.data[rowNum][colNum] = one.data[rowNum][colNum] + two.data[rowNum][colNum];

            }

        }

        // Returning the new matrix
        return newMat;

    }

    // ----------------------------------------------Subtraction--------------------------------------------------------------------

    // TODO Update subtraction to make them faster

    // Element subtraction
    public Matrix sub(double scalar) {

        // The way that I will handle subtraction is to just multiply by negative 1 and then 
        return this.add(-scalar);

    }

    public Matrix sub(Matrix other) {

        return this.elementMult(-1).add(other).elementMult(-1);

    }

    // ----------------------------------------------Misc--------------------------------------------------------------------
    
    // The transposition method
    public Matrix transpose() {

        // The new matrix that we are returning
        Matrix newMat = new Matrix();
        newMat.cols = this.rows;
        newMat.rows = this.cols;
        newMat.data = new double[this.cols][this.rows];

        // Looping for every value in the matrix to move it to the new location
        for (int rowNum = 0; rowNum < this.rows; rowNum++) {

            for (int colNum = 0; colNum < this.cols; colNum++) {

                newMat.data[colNum][rowNum] = this.data[rowNum][colNum];

            }

        }

        // Setting this to have the new data
        this.data = newMat.data;
        this.cols = newMat.cols;
        this.rows = newMat.rows;

        // Returning self;
        return this;

    }

    // The static transposition method
    public static Matrix transpose(Matrix mat) {

        // The new matrix that we are returning
        Matrix newMat = new Matrix();
        newMat.cols = mat.rows;
        newMat.rows = mat.cols;
        newMat.data = new double[mat.cols][mat.rows];

        // Looping for every value in the matrix to move it to the new location
        for (int rowNum = 0; rowNum < mat.rows; rowNum++) {

            for (int colNum = 0; colNum < mat.cols; colNum++) {

                newMat.data[colNum][rowNum] = mat.data[rowNum][colNum];

            }

        }

        // Returning self;
        return newMat;

    }

    // The toArray method that returns a copy of the data
    public double[][] toArray() {

        // The array that we are going to return
        double[][] arr = new double[this.rows][this.cols];

        // Running a deep copy 
        for (int i = 0; i < this.rows; i++) {

            arr[i] = Arrays.copyOf(this.data[i], this.data[i].length);

        }

        // Returning
        return arr;

    }

    // The static toArray method
    public static double[][] toArray(Matrix mat) {

        // The array that we are going to return
        double[][] arr = new double[mat.rows][mat.cols];

        // Running a deep copy 
        for (int i = 0; i < mat.rows; i++) {

            arr[i] = Arrays.copyOf(mat.data[i], mat.data[i].length);

        }

        // Returning
        return arr;

    }

    // A toArray method, just it returns a vector(1D array)
    public double[] toVector() {

        // We can only do this if one of the dimensions is zero, so here we check
        if (this.rows == 1) {

            // The array that we are going to return
            double[] arr = new double[this.cols];

            // Copying over the data
            for (int i = 0; i < this.cols; i++) {

                arr[i] = this.data[0][i];

            }

            // Returning
            return arr;

        } else if (this.cols == 1) {

            // The array that we are going to return
            double[] arr = new double[this.rows];

            // Copying over the data
            for (int i = 0; i < this.rows; i++) {

                arr[i] = this.data[i][0];

            }

            // Returning
            return arr;
            

        } else {

            // Logging the error of their ways
            System.out.println("The toVector method can only run for a 1D Matrix (AKA a vector).\nThis Matrix is a " + this.rows + " by " + this.cols + " Matrix, so it is not a vector.");
            throw new Error("toVectorSizeError");

        }

        

    }


    // A soft max function that normalizes the Matrix to percentage values less than one
    public Matrix softMax() {

        // First we need to find the sum of the every element when e is raised to their power (weird, I know)
        double sum = 0;
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {

                // Math :)
                sum += Math.exp(this.data[row][col]);

            }
        }

        // Now we can replace every element with e raised to the element over the sum. Simple
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {

                // Math :)
                this.data[row][col] = Math.exp(this.data[row][col]) / sum;

            }
        }
        
        // Returning self
        return this;

    }

    // The static soft max function
    public static Matrix softMax(Matrix mat) {

        // First we need to find the sum of the every element when e is raised to their power (weird, I know)
        double sum = 0;
        for (int row = 0; row < mat.rows; row++) {
            for (int col = 0; col < mat.cols; col++) {

                // Math :)
                sum += Math.exp(mat.data[row][col]);

            }
        }

        // The new matrix that we will be editing and returning
        Matrix newMat = new Matrix();
        newMat.rows = mat.rows;
        newMat.cols = mat.cols;

        // Now we can replace every element with e raised to the element over the sum. Simple
        for (int row = 0; row < mat.rows; row++) {
            for (int col = 0; col < mat.cols; col++) {

                // Math :)
                newMat.data[row][col] = Math.exp(mat.data[row][col]) / sum;

            }
        }
        
        // Returning the edited one
        return newMat;

    }
    
    // The mapping function 
    public Matrix map(activationFunction func) {

        // We want to loop through the Matrix now and run each element through the function
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {

                this.data[row][col] = func.run(this.data[row][col]);

            }
        }

        // Returning self
        return this;

    }

    // The static mapping function
    public static Matrix map(Matrix mat, activationFunction func) {

        // Making a new matrix that we will edit and return
        Matrix newMat = new Matrix();
        newMat.rows = mat.rows;
        newMat.cols = mat.cols;
        newMat.data = new double[mat.rows][mat.cols];

        // We want to loop through the Matrix now and run each element through the function
        for (int row = 0; row < newMat.rows; row++) {
            for (int col = 0; col < newMat.cols; col++) {

                newMat.data[row][col] = func.run(mat.data[row][col]);

            }
        }

        // Returning the new matrix
        return newMat;

    }

    // This is just a blank interface so that you can create a lambda expression to pass in to the mapping function
    public interface activationFunction {
        double run(double x);
    }

} 


