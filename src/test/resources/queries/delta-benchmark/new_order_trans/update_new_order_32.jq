for $d in delta-file("../../../queries/delta_benchmark_data/newOrderTable32")
return replace value of $d.NO_O_ID with ($d.NO_O_ID + 1)