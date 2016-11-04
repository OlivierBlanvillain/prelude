import scala.language.implicitConversions

package object prelude {
  @inline def identity[A](x: A): A = x
  @inline def implicitly[T](implicit e: T) = e
  @inline def print(x: Any)   = java.lang.System.out.print(x)
  @inline def println()       = java.lang.System.out.println()
  @inline def println(x: Any) = java.lang.System.out.println(x)
  @inline def ??? : Nothing = throw new NotImplementedError

  @scala.annotation.elidable(scala.annotation.elidable.ASSERTION)
  @inline def assert(assertion: Boolean): Unit =
    if (!assertion) throw new java.lang.AssertionError("assertion failed")

  @scala.annotation.elidable(scala.annotation.elidable.ASSERTION)
  @inline def assert(assertion: Boolean, message: => Any): Unit =
    if (!assertion) throw new java.lang.AssertionError(s"assertion failed: $message")

  implicit final class ArrowAssoc[A](private val self: A) extends AnyVal {
    @inline def -> [B](y: B): (A, B) = (self, y)
  }

  type <:< [-From, +To] = Predef.<:<[From, To]
  type =:= [From, To]   = Predef.=:=[From, To]
  @inline implicit def preludeConforms[A]: A <:< A = Predef.$conforms[A]
  @inline implicit def preludeByteWrapper(x: Byte)     = new scala.runtime.RichByte(x)
  @inline implicit def preludeShortWrapper(x: Short)   = new scala.runtime.RichShort(x)
  @inline implicit def preludeIntWrapper(x: Int)       = new scala.runtime.RichInt(x)
  @inline implicit def preludeCharWrapper(c: Char)     = new scala.runtime.RichChar(c)
  @inline implicit def preludeLongWrapper(x: Long)     = new scala.runtime.RichLong(x)
  @inline implicit def preludeFloatWrapper(x: Float)   = new scala.runtime.RichFloat(x)
  @inline implicit def preludeDoubleWrapper(x: Double) = new scala.runtime.RichDouble(x)

  type Any                 = scala.Any
  type AnyVal              = scala.AnyVal
  type AnyRef              = scala.AnyRef
  type Nothing             = scala.Nothing
  type Null                = scala.Null
  type App                 = scala.App
  val  Array               = scala.Array
  type Array[A]            = scala.Array[A]
  val  Console             = scala.Console
  type Dynamic             = scala.Dynamic
  type Equals              = scala.Equals
  type MatchError          = scala.MatchError
  type NotImplementedError = scala.NotImplementedError
  val  PartialFunction     = scala.PartialFunction
  type PartialFunction[A, B] = scala.PartialFunction[A, B]
  val  Predef              = scala.Predef
  type Product             = scala.Product
  val  StringContext       = scala.StringContext
  type StringContext       = scala.StringContext
  type Serializable        = scala.Serializable
  val  Symbol              = scala.Symbol
  type Symbol              = scala.Symbol
  val  Function            = scala.Function
  type Function0[A]        = scala.Function0[A]
  type Function1[A, B]     = scala.Function1[A, B]
  type Tuple1[A]           = scala.Tuple1[A]
  val  Tuple1              = scala.Tuple1
  type Tuple2[A, B]        = scala.Tuple2[A, B]
  val  Tuple2              = scala.Tuple2

  val  Boolean             = scala.Boolean
  type Boolean             = scala.Boolean
  val  Byte                = scala.Byte
  type Byte                = scala.Byte
  val  Char                = scala.Char
  type Char                = scala.Char
  val  Double              = scala.Double
  type Double              = scala.Double
  val  Float               = scala.Float
  type Float               = scala.Float
  val  Int                 = scala.Int
  type Int                 = scala.Int
  val  Long                = scala.Long
  type Long                = scala.Long
  val  Short               = scala.Short
  type Short               = scala.Short
  val  Unit                = scala.Unit
  type Unit                = scala.Unit

  type deprecated  = scala.deprecated
  type inline      = scala.inline
  type native      = scala.native
  type noinline    = scala.noinline
  type specialized = scala.specialized
  type transient   = scala.transient
  type unchecked   = scala.unchecked
  type volatile    = scala.volatile
  type tailrec     = scala.annotation.tailrec

  val  Option         = scala.Option
  type Option[A]      = scala.Option[A]
  val  None           = scala.None
  val  Some           = scala.Some
  type Some[A]        = scala.Some[A]
  type Either[+A, +B] = scala.util.Either[A, B]
  val  Either         = scala.util.Either
  type Left[+A, +B]   = scala.util.Left[A, B]
  val  Left           = scala.util.Left
  type Right[+A, +B]  = scala.util.Right[A, B]
  val  Right          = scala.util.Right

  type String    = java.lang.String
  type Throwable = java.lang.Throwable
  type Exception = java.lang.Exception
  type Error     = java.lang.Error

  type AssertionError                  = java.lang.AssertionError
  type ArithmeticException             = java.lang.ArithmeticException
  type IllegalStateException           = java.lang.IllegalStateException
  type RuntimeException                = java.lang.RuntimeException
  type NullPointerException            = java.lang.NullPointerException
  type ClassCastException              = java.lang.ClassCastException
  type IndexOutOfBoundsException       = java.lang.IndexOutOfBoundsException
  type ArrayIndexOutOfBoundsException  = java.lang.ArrayIndexOutOfBoundsException
  type StringIndexOutOfBoundsException = java.lang.StringIndexOutOfBoundsException
  type UnsupportedOperationException   = java.lang.UnsupportedOperationException
  type IllegalArgumentException        = java.lang.IllegalArgumentException
  type NoSuchElementException          = java.util.NoSuchElementException
  type NumberFormatException           = java.lang.NumberFormatException
  type AbstractMethodError             = java.lang.AbstractMethodError
  type InterruptedException            = java.lang.InterruptedException

  import scala.collection.generic.CanBuildFrom
  import scala.collection.immutable.StringOps
  import scala.Predef.StringCanBuildFrom

  @inline implicit val preludeStringCanBuildFrom: CanBuildFrom[String, Char, String] = StringCanBuildFrom
  @inline implicit def preludeAugmentString(x: String): StringOps = new StringOps(x)
  @inline implicit def preludeUnaugmentString(x: StringOps): String = x.repr

  import scala.collection.mutable.ArrayOps

  implicit def preludeGenericArrayOps[T](xs: Array[T]): ArrayOps[T] = xs match {
    case x: Array[AnyRef]  => preludeRefArrayOps[AnyRef](x).asInstanceOf[ArrayOps[T]]
    case x: Array[Int]     => preludeIntArrayOps(x)        .asInstanceOf[ArrayOps[T]]
    case x: Array[Double]  => preludeDoubleArrayOps(x)     .asInstanceOf[ArrayOps[T]]
    case x: Array[Long]    => preludeLongArrayOps(x)       .asInstanceOf[ArrayOps[T]]
    case x: Array[Float]   => preludeFloatArrayOps(x)      .asInstanceOf[ArrayOps[T]]
    case x: Array[Char]    => preludeCharArrayOps(x)       .asInstanceOf[ArrayOps[T]]
    case x: Array[Byte]    => preludeByteArrayOps(x)       .asInstanceOf[ArrayOps[T]]
    case x: Array[Short]   => preludeShortArrayOps(x)      .asInstanceOf[ArrayOps[T]]
    case x: Array[Boolean] => preludeBooleanArrayOps(x)    .asInstanceOf[ArrayOps[T]]
    case x: Array[Unit]    => preludeUnitArrayOps(x)       .asInstanceOf[ArrayOps[T]]
    case null              => null
  }

  implicit def preludeRefArrayOps[T <: AnyRef](xs: Array[T]):       ArrayOps[T]       = new ArrayOps.ofRef[T](xs)
  implicit def preludeIntArrayOps             (xs: Array[Int]):     ArrayOps[Int]     = new ArrayOps.ofInt(xs)
  implicit def preludeDoubleArrayOps          (xs: Array[Double]):  ArrayOps[Double]  = new ArrayOps.ofDouble(xs)
  implicit def preludeLongArrayOps            (xs: Array[Long]):    ArrayOps[Long]    = new ArrayOps.ofLong(xs)
  implicit def preludeFloatArrayOps           (xs: Array[Float]):   ArrayOps[Float]   = new ArrayOps.ofFloat(xs)
  implicit def preludeCharArrayOps            (xs: Array[Char]):    ArrayOps[Char]    = new ArrayOps.ofChar(xs)
  implicit def preludeByteArrayOps            (xs: Array[Byte]):    ArrayOps[Byte]    = new ArrayOps.ofByte(xs)
  implicit def preludeShortArrayOps           (xs: Array[Short]):   ArrayOps[Short]   = new ArrayOps.ofShort(xs)
  implicit def preludeBooleanArrayOps         (xs: Array[Boolean]): ArrayOps[Boolean] = new ArrayOps.ofBoolean(xs)
  implicit def preludeUnitArrayOps            (xs: Array[Unit]):    ArrayOps[Unit]    = new ArrayOps.ofUnit(xs)
}
