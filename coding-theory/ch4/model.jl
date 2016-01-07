using Gadfly, Distributions

function Q(x) 0.5 * erfc(x / sqrt(2)) end

# генерируем все кодовые слова
function getAllCodeWords(k, n, G)
  C = Array(Int64, 2^k, n)
  ind = 1
  for i in 1:k # перебираем все сочетания строк, чтобы получить кодовые слова
    for comb in combinations(1:k, i)
      res = Array(Int64, n)'
      fill!(res, 0)
      for c in comb
        res += G[c, :]
      end

      res = map(x -> x % 2, res)
      C[ind, :] = res

      ind += 1
    end
  end
  for i = 1:n
    C[2^k, i] = 0
  end

  return C
end

function addErrors(n, c, errProb)
  e = rand(Uniform(0, 1), n)
  for i = 1:n
    if e[i] <= errProb  # вносим ошибку согласно вероятности
      c[i] = 1
    end
  end
end

# функция декодирования по МАВ
function guessCode(y, C, errProb)
  codeNum = length(C[:, 1])
  bestProb = -1
  guessIndex = -1

  for i in 1:codeNum
    curProb = 1
    for j in 1:length(y)
      if y[j] == C[i, j]
        curProb *= (1 - errProb)
      else
        curProb *= errProb
      end
    end

    if curProb > bestProb
      bestProb = curProb
      guessIndex = i
    end
  end

  return guessIndex
end

function errorProbOnBit(errProb, n, k, m, C, matrNum)
  result = 0

  println("num: $matrNum, errProb = $errProb: start")
  c = Array(Int64, n) # кодовое слово длины n
  for i = 1:m
    fill!(c, 0) # берём нулевое кодовое слово
    addErrors(n, c, errProb) # передаём, внося ошибки
    wordNum = guessCode(c, C, errProb) # декодируем по МАВ

    oneBits = 0
    for j = 1:n # считаем число ошибочныx бит
      if C[wordNum, j] == 1
        oneBits += 1
      end
    end

    result += oneBits / k # вероятность ошибки на бит
  end
  println("done")

  return result / m # усредняем по числу экспериментов
end

# вариант 68
GK = [[1 0 0 0 0 0 1 0 1 1],
      [0 1 0 0 0 0 1 1 1 1],
      [0 0 1 0 0 0 1 1 1 0],
      [0 0 0 1 0 0 0 1 1 1],
      [0 0 0 0 1 0 1 0 0 1],
      [0 0 0 0 0 1 0 1 0 1]]

n = length(GK[1, :]) # n – число столбцов
k = length(GK[:, 1]) # k – длина блока (число строк)
m = 100000 # число экспериментов

sdb = [0:0.1:10] # SNR, dB per bit
sb = 10 .^ (sdb / 10) # SNR per bit
sig = sqrt(0.5 ./ sb)
sig1 = 1 ./ sig
PE = Q(sig1)

C = getAllCodeWords(k, n, GK)
errPerBit1 = map(pe -> errorProbOnBit(pe, n, k, m, C, 1), PE)

peplot = plot(layer(x=sdb, y=errPerBit1, Theme(default_color=colorant"blue"), Geom.line),
              Guide.xticks(ticks=[0:1:10]), Guide.title("m = 10^5"), Scale.y_log10,
              Guide.xlabel("E/N0 (dB)"), Guide.ylabel("Ошибка на бит"))

draw(PDF("m100000_log.pdf", 4inch, 3inch), peplot)
