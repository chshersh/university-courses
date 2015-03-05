using Contour, Gadfly, DataFrames

using OneDimensional: goldenSectionSearch, fibSearch

f(x, y) = x^4 + y^4 - 5 * (x * y - x^2 * y^2)
fdx(x, y) = 4x^3 - 5y + 10 * x * y^2
fdy(x, y) = 4y^3 - 5x + 10 * y * x^2
▽(x, y) = [fdx(x, y), fdy(x, y)]
L(x, y, λ) = [x, y] .- (λ .* ▽(x, y))

function simpleGradientDescent(f, ▽, x0, y0, ɛ) # down gradient with exponentital slow step
  callCnt, gradCallCnt, xs, ys = 1, 0, [x0], [y0]

  λ = 0.05
  fprev = f(x0, y0)
  while true
    xk, yk = xs[end], ys[end]
    xnew, ynew = L(xk, yk, λ)
    gradCallCnt += 1
    if xnew < 0 || 1 < xnew || ynew < 0 || 1 < ynew
      λ *= 0.75
      continue
    end
    fnew = f(xnew, ynew)
    callCnt += 1
    if fnew > fprev
      λ *= 0.75
      continue
    end
    fprev = fnew
    push!(xs, xnew)
    push!(ys, ynew)
    if norm([xs[end] - xs[end - 1], ys[end] - ys[end - 1]]) < ɛ
      break
    end
  end
  xs, ys, callCnt, gradCallCnt
end

function fastGradienDescent(f, ▽, x0, y0, ɛ) # fastest descent gradient
  callCnt, gradCallCnt, xl, yl = 1, 0, [x0], [y0]

  left, right = 0, 1
  while true
    x_left, y_left = xl[end], yl[end]
    g = ▽(x_left, y_left)
    gradCallCnt += 1
    ax = [(left - x_left) / -g[1], (right - x_left) / -g[1]]
    ay = [(left - y_left) / -g[2], (right - y_left) / -g[2]]
    sort!(ax)
    sort!(ay)
    a = [max(ax[1], ay[1]), min(ax[2], ay[2])]
    λ, cnt, _, _, _ = fibSearch(arg -> f(x_left - arg * g[1], y_left - arg * g[2]), a[1], a[2], ɛ)
    callCnt += cnt
    xnew, ynew = x_left - λ * g[1], y_left - λ * g[2]
    push!(xl, xnew)
    push!(yl, ynew)
    if norm([xl[end] - xl[end - 1], yl[end] - yl[end - 1]]) < ɛ
      break
    end
  end
  xl, yl, callCnt, gradCallCnt
end

ɛ = 0.0001
x0, y0 = 0.2, 0.8
sgx, sgy, cnt1, gcnt1 = simpleGradientDescent(f, ▽, x0, y0, ɛ)
fgx, fgy, cnt2, gcnt2 = fastGradienDescent(f, ▽, x0, y0, ɛ)

@printf("Обычный градиентный спуск: %d вычислений функции, %d вычислений градиента\n", cnt1, gcnt1)
@printf("Наискорейший спуск: %d вычислений функции, %d вычислений градиента\n", cnt2, gcnt2)

Gadfly.set_default_plot_size(18cm, 18cm)
plot(layer(x=[0:0.01:1], y=[0:0.01:1], z=f, Geom.contour(levels=15)),
     layer(x=sgx, y=sgy, Theme(default_color=color("orange")), Geom.point, Geom.line),
     layer(x=fgx, y=fgy, Theme(default_color=color("red")), Geom.point, Geom.line(preserve_order=true)),
     Guide.xticks(ticks=[0:0.2:1.0]), Guide.yticks(ticks=[0:0.2:1.0]),
     Guide.title("Линии уровня функции x^4 + y^4 - 5(xy - x^2 * y^2)"))
