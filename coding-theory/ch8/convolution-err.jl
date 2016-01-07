using Gadfly

DF68 = 8 # свободное расстояние кода, вариант 68

FCoefs = [3, 15, 58, 201, 655, 2052, 6255, 18687, 54974, 159765]
F68Powers = [8:2:26]

function Q(x) 
    0.5 * erfc(x / sqrt(2)) 
end


function Peb(df, B, D0, F, p0) 
    B(df, p0) * F(D0(p0)) 
end


function dskB(w, p0) 
    (1 - p0) / (1 - 2p0) * sqrt(2 / (π * w)) 
end

function dskD0(p0) 
    2 * sqrt(p0 * (1 - p0)) 
end 


function abgshB(w, sg) 
    Q(sqrt(2 * w * sg)) * exp(w * sg) 
end

function abgshD0(sg) 
    exp(-sg) 
end


function F68(D) 
    (D^8 * (D^4 - 3D^2 + 3)) / (D^4 - 3D^2 + 1)^2 
end

function F68_5(D)
    dot(FCoefs[1:5], map(i -> D^i, F68Powers[1:5]))
end

function F68_10(D)
    dot(FCoefs, map(i -> D^i, F68Powers))
end

sdb = [-2:0.01:10] # SNR, dB per bit
sb = 10 .^ (sdb / 10) # SNR per bit
sig = sqrt(0.5 ./ sb)
sig1 = 1 ./ sig
p0 = Q(sig1)


dskPEB68      = map(p -> Peb(DF68, dskB, dskD0, F68, p), p0)
dskPEB68_5    = map(p -> Peb(DF68, dskB, dskD0, F68_5, p), p0)
dskPEB68_10   = map(p -> Peb(DF68, dskB, dskD0, F68_10, p), p0)

abgshPEB68      = map(p -> Peb(DF68, abgshB, abgshD0, F68, p), sb)
abgshPEB68_5    = map(p -> Peb(DF68, abgshB, abgshD0, F68_5, p), sb)
abgshPEB68_10   = map(p -> Peb(DF68, abgshB, abgshD0, F68_10, p), sb)

                  
gplot = plot(layer(x=sdb, y=dskPEB68,      Theme(default_color=colorant"blue"), Geom.line),
             layer(x=sdb, y=dskPEB68_5,    Theme(default_color=colorant"red"), Geom.line),
             layer(x=sdb, y=dskPEB68_10,   Theme(default_color=colorant"green"), Geom.line),
             layer(x=sdb, y=abgshPEB68,    Theme(default_color=colorant"orange"), Geom.line),
             layer(x=sdb, y=abgshPEB68_5,  Theme(default_color=colorant"black"), Geom.line),
             layer(x=sdb, y=abgshPEB68_10, Theme(default_color=colorant"purple"), Geom.line),
             Guide.manual_color_key("Графики",
                                   ["ДСК, F", "ДСК, 5", "ДСК, 10", "АБГШ, F", "АБГШ, 5", "АБГШ, 10"],
                                   ["blue", "red", "green", "orange", "black", "purple"]),
             Theme(default_color=colorant"black"),
             Guide.xticks(ticks=[-2:1:10]), Scale.y_log10,
             Guide.xlabel("E/N0 (dB)"), Guide.ylabel("Вероятность ошибки на бит"))

draw(PDF("errors_all.pdf", 6inch, 4inch), gplot)
