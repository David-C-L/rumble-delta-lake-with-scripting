for $d in delta-file("../../../queries/delta_benchmark_data/bigghTables/bigghTable8")
return insert { "nickname" : "cool_nickname", "popularity_rating" : -1 } into $d.repo