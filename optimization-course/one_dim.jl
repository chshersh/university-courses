module OneDimensional

using DataFrames, Gadfly

export goldenSectionSearch, fibSearch

function ternarySearch(f, left, right, ɛ)
  callCnt, ls, rs, rels = 0, [left], [right], [1.0]

  while right - left > ɛ
    a = (left * 2 + right) / 3
    b = (left + right * 2) / 3
    if f(a) < f(b)
      right = b
    else
      left = a
    end
    push!(rels, (right - left) / (rs[end] - ls[end]))
    push!(ls, left)
    push!(rs, right)
    callCnt += 2
  end
  (left + right) / 2.0, callCnt, ls, rs, rels
end

function dichotomySearch(f, left, right, ɛ)
  callCnt, ls, rs, rels = 0, [left], [right], [1.0]

  δ = ɛ / 4
  while right - left > ɛ
    a = (left + right - δ) / 2
    b = (left + right + δ) / 2
    if f(a) < f(b)
      right = b
    else
      left = a
    end
    push!(rels, (right - left) / (rs[end] - ls[end]))
    push!(ls, left)
    push!(rs, right)
    callCnt += 2
  end
  (left + right) / 2.0, callCnt, ls, rs, rels
end

function goldenSectionSearch(f, left, right, ɛ)
  callCnt, ls, rs, rels = 2, [left], [right], [1.0]

  resphi = 2 - φ
  a = left + resphi * (right - left)
  b = right - resphi * (right - left)
  fa, fb = f(a), f(b)

  while right - left > ɛ
    if fa < fb
      right = b
      b = a
      fb = fa
      a = left + resphi * (right - left)
      fa = f(a)
    else
      left = a
      a = b
      fa = fb
      b = right - resphi * (right - left)
      fb = f(b)
    end
    push!(rels, (right - left) / (rs[end] - ls[end]))
    push!(ls, left)
    push!(rs, right)
    callCnt += 1
  end
  (left + right) / 2, callCnt, ls, rs, rels
end

function fibSearch(f, left, right, ɛ)
  callCnt, ls, rs, rels = 2, [left], [right], [1.0]

  fibs = [1, 2]
  while (right - left) / fibs[end] > ɛ
    push!(fibs, fibs[end] + fibs[end - 1])
  end

  x1 = left + (right - left) * fibs[end - 2] / fibs[end]
  #x2 = left + (right - left) * fibs[end - 1] / fibs[end]
  x2 = left + right - x1
  f1, f2 = f(x1), f(x2)

  for k=1:length(fibs)-3
    if f1 < f2
      right = x2
      x2 = x1
      #x1 = left + (right - left) * fibs[end - k - 2] / fibs[end - k]
      x1 = left + right - x2
      f2 = f1
      f1 = f(x1)
    else
      left = x1
      x1 = x2
      #x2 = left + (right - left) * fibs[end - k - 1] / fibs[end - k]
      x2 = left + right - x1
      f1 = f2
      f2 = f(x2)
    end
    push!(rels, (right - left) / (rs[end] - ls[end]))
    push!(ls, left)
    push!(rs, right)
    callCnt += 1
  end
  (left + right) / 2, callCnt, ls, rs, rels
end

function uniformSearch(f, left::Float64, right::Float64, n::Int32)
  fs = [f(left + i * (right - left) / (n + 1)) for i in 0:n+1]
  minval, j, = fs[1], 0
  for i in 2:length(fs)
    if minval > fs[i]
      minval, j = fs[i], i - 1
    end
  end
  left + j * (right - left) / (n + 1), minval, n + 1
end

function uniformSearch(f, left::Float64, right::Float64, ɛ::Float64)
  n = convert(Int32, (right - left) / ɛ - 1.0)
  return uniformSearch(f, left, right, n)
end

function uniformSequenceSearch(f, left, right, n::Int64, ɛ)
  callCnt, ls, rs, rels = 0, [left], [right], [1.0]

  while right - left > ɛ
    fs = [f(left + i * (right - left) / n) for i in 0:n]
    minval, j = fs[1], 1
    for i in 2:length(fs)
      if minval > fs[i]
        minval, j = fs[i], i - 1
      end
    end

    if j == 0
      right = left + (right - left) / n
    elseif j == length(fs) - 1
      left = right - (right - left) / n
    else
      a = left + (j - 1) * (right - left) / n
      b = left + (j + 1) * (right - left) / n
      left, right = a, b
    end

    push!(rels, (right - left) / (rs[end] - ls[end]))
    push!(ls, left)
    push!(rs, right)
    callCnt += n + 1
  end
  (left + right) / 2, callCnt, ls, rs, rels
end

type Line
  a :: Float64
  b :: Float64
  c :: Float64
end

function polylineSearch(f, L, left, right, ɛ)
  lineLipshitz(L, fx0, x0) = Line(-L, 1, L * x0 - fx0)

  function segmentLine(x1, y1, x2, y2)
    a = y1 - y2
    b = x2 - x1
    c = -a * x1 - b * y1
    Line(a, b, c)
  end

  function lineIntersection(l1::Line, l2::Line)
    Δ = det([[l1.a, l1.b] [l2.a, l2.b]])
    det([[-l1.c, l1.b] [-l2.c, l2.b]]) / Δ, det([[l1.a, -l1.c] [l2.a, -l2.c]]) / Δ
  end

  function minDiff(yc, fs)
    miny, j = yc[1], 1
    for i in 2:length(yc)
      if miny > yc[i]
        miny, j = yc[i], i
      end
    end
    fs[j] - yc[j]
  end

  fl, fr = f(left), f(right)
  pnt = lineIntersection(lineLipshitz(-L, fl, left), lineLipshitz(L, fr, right))
  xc = [left, pnt[1], right]
  yc = [fl, pnt[2], fr]
  fs = [fl, f(xc[2]), fr]
  callCnt = 3

  while minDiff(yc, fs) > ɛ
    # finding minimum value through polyline
    minval, j = yc[1], 1
    for i = 2:length(yc)
      if minval > yc[i]
        minval = yc[i]
        j = i
      end
    end

    # create two tangents
    newPike = f(xc[j])
    l1, l2 = lineLipshitz(-L, newPike, xc[j]), lineLipshitz(L, newPike, xc[j])
    callCnt += 1

    # cnage polyline insertint two new segments
    pnt1 = lineIntersection(l2, segmentLine(xc[j], yc[j], xc[j - 1], yc[j - 1]))
    pnt2 = lineIntersection(l1, segmentLine(xc[j], yc[j], xc[j + 1], yc[j + 1]))

    xc = [xc[1:j - 1]; pnt1[1]; xc[j]; pnt2[1]; xc[j + 1:end]]
    yc = [yc[1:j - 1]; pnt1[2]; newPike; pnt2[2]; yc[j + 1:end]]
    fs = [fs[1:j - 1]; f(pnt1[1]); newPike; f(pnt2[1]); xc[j + 1:end]]
    callCnt += 2
  end
  xc, yc, callCnt
end

minx, callNum, ls, rs, rels = ternarySearch(x -> x^2 + 2x, -5.0, 5.0, 0.001)
#df = DataFrame(L=ls, R=rs, Rel=rels)
#writetable("ternary.txt", df)
minx, callNum, ls, rs, rels = dichotomySearch(x -> x^2 + 2x, -5.0, 5.0, 0.001)
#df = DataFrame(L=ls, R=rs, Rel=rels)
#writetable("dichotomy.txt", df)
minx, callNum, ls, rs, rels = goldenSectionSearch(x -> x^2 + 2x, -5.0, 5.0, 0.001)
#df = DataFrame(L=ls, R=rs, Rel=rels)
#writetable("goldenSection.txt", df)
minx, callNum, ls, rs, rels = fibSearch(x -> x^2 + 2x, -5.0, 5.0, 0.001)
#df = DataFrame(L=ls, R=rs, Rel=rels)
#writetable("fib.txt", df)
#minf, fval = uniformSearch(x -> x^2 + 2x, -5.0, 5.0, 0.1)
minx, callNum, ls, rs, rels = uniformSequenceSearch(x -> x^2 + 2x, -5.0, 5.0, 5, 0.001)
#df = DataFrame(L=ls, R=rs, Rel=rels)
#writetable("uniform.txt", df)

eps = [0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000000001]
ds, gs, fs, us = Int32[], Int32[], Int32[], Int32[]
f(x) = x^2 + 2x
for ɛ in eps
  _, dscnt, _, _, _ = dichotomySearch(f, -5.0, 5.0, ɛ)
  _, gscnt, _, _, _ = goldenSectionSearch(f, -5.0, 5.0, ɛ)
  _, fscnt, _, _, _ = fibSearch(f, -5.0, 5.0, ɛ)
  _, uscnt, _, _, _ = uniformSequenceSearch(f, -5.0, 5.0, 5, ɛ)
  push!(ds, dscnt)
  push!(gs, gscnt)
  push!(fs, fscnt)
  push!(us, uscnt)
end

dsit = [calls / 2.0 for calls in ds]
gsit = [calls - 2 for calls in gs]
fsit = [calls - 2 for calls in fs]
usit = [calls / 6 for calls in fs]

plot(layer(x=dsit, y=ds, Theme(default_color=color("purple")), Geom.point, Geom.line),
     layer(x=gsit, y=gs, Theme(default_color=color("orange")), Geom.point, Geom.line),
     layer(x=fsit, y=fs, Theme(default_color=color("green")), Geom.point, Geom.line),
     layer(x=usit, y=us, Theme(default_color=color("red")), Geom.point, Geom.line),
     Guide.xticks(ticks=[0:5:60]), Guide.yticks(ticks=[0:10:150]),
     Guide.title("Связь числа итераций и числа вычислений функции"),
     Guide.xlabel("Число итераций"), Guide.ylabel("Число вызовов функций"))

epsLogs = [-log(ɛ) for ɛ in eps]

plot(layer(x=epsLogs, y=ds, Theme(default_color=color("purple")), Geom.point, Geom.line),
     layer(x=epsLogs, y=gs, Theme(default_color=color("orange")), Geom.point, Geom.line),
     layer(x=epsLogs, y=fs, Theme(default_color=color("green")), Geom.point, Geom.line),
     layer(x=epsLogs, y=us, Theme(default_color=color("red")), Geom.point, Geom.line),
     Guide.title("Зависимость числа вызовов функции от минус логарифма точности"),
     Guide.xlabel("Точность"), Guide.ylabel("Число вызовов функций"))

xlines, ylines, cntCall = polylineSearch(x -> 0.5 * x^2 * sin(2x), 6, -3.0, 3.0, 0.5)

Gadfly.set_default_plot_size(12cm, 12cm)
plot(layer(x -> 0.5 * x^2 * sin(2x), -3, 3),
     layer(x=xlines, y=ylines, Theme(default_color=color("orange")), Geom.point, Geom.line),
     Guide.xticks(ticks=[-3:1:3]),
     Guide.title("Метод ломанныx функции y=1/2 ⋅ x^2 ⋅ sin(2x)"),
     Theme(default_color=color("purple")))

end
