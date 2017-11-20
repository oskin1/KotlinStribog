import com.sun.xml.internal.fastinfoset.util.StringArray
import unsigned.*
import kotlin.experimental.and
import kotlin.experimental.xor

class Stribog {

    fun GOSTHashGetKey(k : Array<Ubyte>, i : Int)
    {
        var a = gostX(k, strArr2ByteArr(const[i]));
        a = gostS(a);
        a = gostP(a);
    }

    fun gostX(firstVect : Array<Ubyte>, secondVect : Array<Ubyte>) : Array<Ubyte>{

        var resultVect : Array<Ubyte> = emptyArray()

        for (i in 0..63) resultVect[i] = firstVect[i].xor(secondVect[i])

        return resultVect

    }

    fun gostAdd512(firstVect : ByteArray, secondVect : ByteArray) : ByteArray{

        var resultVect = byteArrayOf(64)
        var internal = 0

        for(i in 0..63){
            internal = firstVect[i] + secondVect[i] + internal.ushr(8)
            resultVect[i] = internal.toByte()
        }

        return resultVect

    }

    fun gostS(vect : Array<Ubyte>) : Array<Ubyte>{

        var resultVect : Array<Ubyte> = emptyArray()

        for(i in 63 downTo 0){
            resultVect[i] = Ubyte(Pi[vect[i].toInt()].toInt())
        }

        return resultVect
    }

    fun gostP(vect : Array<Ubyte>) : Array<Ubyte>{

        var resultVect : Array<Ubyte> = emptyArray()

        for(i in 63 downTo 0){
            resultVect[i] = Ubyte(vect[Tau[i].toInt()])
        }

        return resultVect
    }

    fun gostL(vect : Array<Ubyte>) : Array<Ulong> {
        var resultVect : Array<Ulong> = arrayOf(
                Ulong("0",16),
                Ulong("0",16),
                Ulong("0",16),
                Ulong("0",16),
                Ulong("0",16),
                Ulong("0",16),
                Ulong("0",16),
                Ulong("0",16)

        )
        for(i in 7 downTo 0){
            for(j in 63 downTo 0){
                if(vect[i*8 + j/8].shr(j % 8).and(1) == Ubyte(1)){
                    resultVect[i] = resultVect[i].xor(Ulong(A[j],16))
                }
            }
        }
        return resultVect
    }

    fun strArr2ByteArr(arr : Array<String>) : Array<Ubyte>{
        var resArr : Array<Ubyte> = emptyArray()
        for(i in 0..(arr.size-1)){
            resArr[i] = Ubyte(arr[i],16)
        }
        return resArr
    }

}