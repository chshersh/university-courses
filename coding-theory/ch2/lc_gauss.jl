function coerceH(H)
  n = length(H[1, :]) # n – число столбцов
  r = length(H[:, 1]) # r – число строк

  # проxод вниз
  for i = 1:r-1
    col = n - r + i # текущий столбец

    row = -1 # первая строка с 1 в текущем столбце
    for j = i:r
      if H[j, col] == 1
        row = j
        break
      end
    end

    findCollumn = false
    if row == -1 # вырожденная подматрица, нужно менять столбцы
      for ii = i:r
        for jj = n-r:1
          if H[ii, jj] == 1
            println("меняем столбцы $col и $jj")
            H[:, col], H[:, jj] = H[:, jj], H[:, col]
            row = ii

            findCollumn = true
            break
          end
        end

        if findCollumn break end
      end
    end

    if row != i # нужно менять строки
      H[i, :], H[row, :] = H[row, :], H[i, :] # меняем строки местами
    end

    for j = i+1:r
      if H[j, col] == 1
        H[j, :] $= H[i, :] # $ — xor
      end
    end
  end

  # проxод вверx
  for i = r:-1:2
    col = n - r + i # текущий столбец

    for j = i-1:-1:1
      if H[j, col] == 1
        H[j, :] $= H[i, :] # $ — xor
      end
    end
  end

  P = H[:, 1:n-r]'  # первые n-k столбцов, ' — транспонирование
  E = int(eye(n-r)) # создаём единичную матрицу размера n-k
  G = [E P]

  return (G, H)
end

function minDistance(G)
  k = length(G[:, 1]) # число строк
  minWeight = 1000000 # какое-то большое число

  for i = 1:2^k-1 # перебираем все вектора кроме 0
    b = collect(reverse(bits(i))[1:k]) # k символов бит
    m = map(parseint, b) # bits возвращает 64 символа, конвертируем в int

    d = m' * G
    d = map(x -> x % 2, d) # берём по модулю 2

    w = sum(d)
    minWeight = min(w, minWeight)
  end

  return minWeight
end

function syndrome(H)
  n = length(H[1, :]) # n – число столбцов
  r = length(H[:, 1]) # r – число строк

  syndromeWeights = fill!(Array(Int64, 2^r), n + 1) # массив весов, заполняем значением n + 1
  S = Array(Int64, 2^r - 1, n)

  for i = 1:2^n-1 # перебираем все вектора кроме 0
    b = collect(reverse(bits(i))[1:n]) # n символов бит
    e = map(parseint, b) # текущий вектор ошибки

    s = e' * H'
    s = map(x -> x % 2, s) # берём по модулю 2
    w = sum(e)
    sind = parseint(join(s, ""), 2) # конвертируем битовый вектор в число

    if w != 0 && sind != 0 && syndromeWeights[sind] > w
      S[sind, :] = e
      syndromeWeights[sind] = w
    end
  end

  return S
end

function toTexTable(M)
  rows    = [M[i, :] for i in 1:length(M[:, 1])]
  texRows = [join(row, " & ") for row in rows]
  endRows = join(texRows, " \\\\ \n")
end

function toSyndromeTable(S)
  tableRows = ["\$0000\$ & \$0000000000\$", ["\$$(bits(i)[61:64])\$ & \$$(join(S[i, :], ""))\$" for i in 1:length(S[:, 1])]]

  join(tableRows, " \\\\ \n")
end

HS = [[1 0 0 1],
     [0 1 0 1],
     [0 0 1 1]]
HK = [[1 0 1 0 1 0 0 1 0 1],
      [0 1 0 0 0 0 1 1 0 1],
      [1 1 1 1 0 0 0 0 1 0],
      [0 1 1 1 0 1 0 1 0 0]]

HTEST = [[1 1 0 0 1 1],
         [0 0 1 0 1 1],
         [0 1 0 1 0 1]]

println(toTexTable(HK))

#S = syndrome(HK)
#println(S)
#println("S = ")
#println(toTexTable(S))
#println(toSyndromeTable(S))

G, H = coerceH(HK)
#w = minDistance(G)

#println("H = ")
#println(H)

println("G = ")
println(G)

#println("H = ")
#println(H)
println(toTexTable(G))

#println("w = $w")
