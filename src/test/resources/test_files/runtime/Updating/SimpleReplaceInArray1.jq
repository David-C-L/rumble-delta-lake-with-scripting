(:JIQS: ShouldRun; Output="[ 1, 2, 5, 4 ]" :)
copy $je := [1 to 4]
modify replace value of $je[[3]] with 5
return $je