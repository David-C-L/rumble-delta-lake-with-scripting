for $d in delta-file("../../../../queries/delta_benchmark_data/bigghTables/bigghTable8")
return replace value of $d.public with (not $d.public)