using Gadfly, Distributions

function Q(x) 0.5 * erfc(x / sqrt(2)) end

sdb = [0:0.1:10] # SNR, dB per bit
sb = 10 .^ (sdb / 10) # SNR per bit
sig = sqrt(0.5 ./ sb)
sig1 = 1 ./ sig
PE = Q(sig1)

peplot = plot(x=sdb, y=PE, Geom.line, Scale.y_log10,
              Theme(default_color=color("black")),
              Guide.xticks(ticks=[0:1:10]),
              Guide.xlabel("E/N0 (dB)"), Guide.ylabel("PE"))

draw(PDF("ctKovanikov6.pdf", 4inch, 3inch), peplot)
