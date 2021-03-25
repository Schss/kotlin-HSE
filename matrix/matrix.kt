class ComplexNumber {
    private val real: Double
    private val im: Double

    constructor(real: Double, im: Double) {
        this.real = real
        this.im = im
    }
    constructor(real: Double) {
        this.real = real
        this.im = 0.0
    }
    constructor() {
        this.real = 0.0
        this.im = 0.0
    }

    override fun toString(): String {
        return if(im != 0.0) {
            real.toString() + " + " + im.toString() + "i"
        } else {
            real.toString()
        }
    }

    operator fun plus(other : ComplexNumber) : ComplexNumber {
        return ComplexNumber(this.real + other.real, this.im + other.im)
    }

    operator fun minus(other : ComplexNumber) : ComplexNumber {
        return ComplexNumber(this.real - other.real, this.im - other.im)
    }

    operator fun times(other : ComplexNumber) : ComplexNumber {
        return ComplexNumber(this.real * other.real - this.im * other.im, this.im * other.real + this.real *
                other.im)
    }

    operator fun times(other : Double) : ComplexNumber {
        return ComplexNumber(this.real * other, this.im * other)
    }
}


class Matrix(val content : Array<ComplexNumber>, val height : Int, val width : Int)
{
    override fun toString() : String {
        var result : String = ""
        for(i in 0 until this.height){
            for(j in 0 until this.width){
                result = result + this.content[j + i * this.width] + ", ";
            }
            result += "\n";
        }
        return result;
    }

    operator fun plus(other: Matrix): Matrix {
        if (width != other.width || height != other.height) throw IllegalArgumentException()
        if (width < 1 || height < 1) return this
        val result = Array(this.width * this.height) {ComplexNumber()}
        for (i in 0 until height) {
            for (j in 0 until width) {
                result[j + i * this.width] = this.content[j + i * this.width] + other.content[j + i * this.width]
            }
        }
        return Matrix(result, this.height, this.width)
    }

    operator fun times(other : ComplexNumber): Matrix {
        if (width < 1 || height < 1) return this
        val result = Array(this.width * this.height) {ComplexNumber()}
        for (i in 0 until height) {
            for (j in 0 until width) {
                result[j + i * this.width] = this.content[j + i * this.width] * other
            }
        }
        return Matrix(result, this.height, this.width)
    }

    operator fun times(other : Matrix): Matrix {
        if (width != other.height) throw IllegalArgumentException()
        if (width < 1 || height < 1) return this
        val result = Array(this.height * other.width) {ComplexNumber()}
        for (i in 0 until height) {
            for (j in 0 until other.width) {
                for(k in 0 until width)
                    result[j + i * other.width] = result[j + i * other.width] + (this.content[k + i * this.width] * other.content[i + k * other.width])
            }
        }
        return Matrix(result, this.height, other.width)
    }

}

fun transpose(matrix: Matrix): Matrix {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = Array(matrix.width * matrix.height) {ComplexNumber()}
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[j + i * matrix.height] = matrix.content[i + j * matrix.width]
        }
    }
    return Matrix(result, matrix.width, matrix.height)
}
/*
fun determinant(matrix: Matrix): Matrix {
    if (matrix.width < 1 || matrix.height < 1) throw IllegalArgumentException()
    if (matrix.width != matrix.height) throw IllegalArgumentException()
}
*/

fun main() {
    val complexReIm = ComplexNumber(15.0, 12.0)
    val complexRe = ComplexNumber(8.0)
    val complexRe2 = ComplexNumber(2.0)

    println("Два объекта класса ComplexNumber.\n")
    println("Объект с действительной и мнимой частью:")
    println(complexReIm)
    println("Объект только с действительной частью:")
    println(complexRe)

    val content1 = arrayOf(complexReIm, complexRe2, complexReIm, complexRe, complexRe, complexRe)
    val content2 = arrayOf(complexRe, complexRe, complexRe2, complexReIm, complexReIm, complexReIm)

    val content3 = arrayOf(complexRe2, complexRe2, complexRe2, complexRe, complexRe, complexRe)
    val content4 = arrayOf(complexRe, complexRe, complexRe2, complexRe2, complexRe2, complexRe2)

    val matrix1 = Matrix(content1, 2, 3)
    val matrix2 = Matrix(content2, 2, 3)

    val matrix3 = Matrix(content3, 2, 3)
    val matrix4 = Matrix(content4, 3, 2)

    println("\nДва обьекта класса Matrix:\n")
    println(matrix1)
    println(matrix2)

    println("Результат сложения матриц:\n")
    println(matrix1 + matrix2)

    println("Результат транспонирования матрицы:\n")
    println(transpose(matrix1))

    println("Результат умножение матрицы на комплексное число:\n")
    println(matrix1 * complexRe2)

    println("\nДва обьекта класса Matrix:\n")
    println(matrix3)
    println(matrix4)

    println("Результат умножения матрицы на матрицу:\n")
    println(matrix3 * matrix4)
}