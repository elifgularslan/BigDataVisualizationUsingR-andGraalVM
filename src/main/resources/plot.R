library(lattice)
data <<- numeric(100)

function(dataHolder) {
    svg()
    data <<- c(data[2:100], dataHolder$value)

    plot <- xyplot(randomData ~ time,
       data = data.frame(randomData = data, time = 0:99),
       main = 'Column-9 Plot',
       ylab = "Random(x)", type = c('l', 'g'), col.line = 'saddlebrown')
    print(plot)
    svg.off()
}