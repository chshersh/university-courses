// функция сложения двух двоичных векторов с приведением
// к одинаковой длине
fun List<Int>.sumWith(b: List<Int>): List<Int> {
    val t = toArrayList()
    while (t.size < b.size) t.add(0)
    for (i in b.indices) {
        t[i] = (t[i] + b[i]) % 2
    }
    return t
}

// функция превращения битового вектора коэффициентов
// в строку в полиномиальном виде
fun List<Int>.toPoly(): String {
    val fst = if (first() == 1) "1" else ""

    val sb = StringBuilder(size)
    for (i in 1..size-1)
        if (this[i] == 1) {
            sb.append("+x")
            if (i > 1) sb.append("^{$i}")
        }

    return "$fst$sb".dropWhile { it == '+' }
}

// объект "строки" таблицы
data class BMStep(
        val r: Int, val x: Int, val delta: Int,
        val B: List<Int>, val loc: List<Int>, val L: Int
) {
    // превращение объекта в строку для разметки LaTeX
    override fun toString(): String {
        return "$r & $x & $delta & \$${B.toPoly()}\$ & \$${loc.toPoly()}\$ & $L \\\\"
    }
}

// Алгоритм Берлекэмпа-Месси
fun bmAlgo(S: List<Int>): List<BMStep> {
    val texTable = arrayListOf<BMStep>()

    var L = 0               // текущая длина регистра
    var loc = listOf(1)     // многочлен локаторов
    var B = arrayListOf(1)  // многочлен компенсации невязки

    for (r in 1..S.size) {
        val delta = loc.zip(S.take(r).reversed()).map { it.first * it.second }.sum() % 2
        B.add(0, 0)  // сдвиг на x вправо
        if (delta != 0) {
            // ЛРОС модифицируется
            val T = loc.sumWith(B)  // вспомогательный многочлен
            if (2 * L <= r - 1) {
                // длина увеличивается
                B = arrayListOf(*loc.toTypedArray())
                L = r - L
            }
            loc = listOf(*T.toTypedArray())
        }

        texTable.add(BMStep(r, S[r - 1], delta, listOf(*B.toTypedArray()), listOf(*loc.toTypedArray()), L))
    }

    return texTable
}

fun main(args: Array<String>) {
    // val S0 = listOf(1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1)
    val S1 = listOf(0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1)
    val S2 = listOf(0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0)
    val S3 = listOf(0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1)
    val S4 = listOf(0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1)


    val table = bmAlgo(S2)
    println("  0 & - & 0 & 1 & 1 & 0 \\\\")
    println("  " + table.drop(12).map { it.toString() }.joinToString(separator = "\n  "))
}