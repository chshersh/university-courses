# граница xэмминга про упаковку шарами
function hammingND(n, d)
  t = div(d - 1, 2)
  binoms = [binomial(n, i) for i in 0:t]
  println("binoms = $binoms")
  z = sum(binoms)
  println("z = $z")
  M = 2^n / z
  return (M, log2(M))
end

function hammingNK(n, k)
  t = 0
  b = 2^(n - k)
  while sum([binomial(n, i) for i in 0:t]) ≤ b
    t += 1
  end
  return t - 1
end

function varshamovGuilbertND(n, d)
  k = 0
  binomSum = sum([binomial(n - 1, i) for i in 0:d-2])
  println("binomSum = $binomSum")

  while 2^(n - k) > binomSum
    k += 1
  end

  return k - 1
end

function varshamovGuilbertNK(n, k)
  d = 2
  b = 2^(n - k)
  while b > sum([binomial(n - 1, i) for i in 0:d-2])
    d += 1
  end
  return d - 1
end

function graismer(k, d)
  n = 0
  for i = 0:k-1
    n += ceil(Int64, d / 2^i)
  end
  return n
end

function graismerD(n, k)
  # n > sum(d/2^i) from 0 to k
  d = 0
  while true
    sums = sum([ceil(Int64, d / 2^i) for i in 0:k-1])
    if n > sums
      d += 1
    else
      break
    end
  end

  return d - 1
end

n, k, d = 21, 13, 14

# наxождение k по n и d
#
# M, klog = hammingND(n, d)
# println("граница Хэмминга: M ≤ $M, k ≈ $(klog)")

# println("==============================")

# mink = varshamovGuilbertND(n, d)
# println("граница Варшамова-Гилберта: k ≥ $mink")

# println("==============================")
# println(graismer(9, d))
# println("###################################")

# наxождение d по n и k
#
t = hammingNK(n, k)
println("граница Хэмминга: t ≤ $t")

println("==============================")

mind = varshamovGuilbertNK(n, k)
println("граница Варшамова-Гилберта: d ≥ $mind")

println("==============================")
println("граница Грайсмера: $(graismerD(n, k))")
println("###################################")

