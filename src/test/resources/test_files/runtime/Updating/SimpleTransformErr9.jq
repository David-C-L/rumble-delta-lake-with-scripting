(:JIQS: ShouldCrash; ErrorCode="XUDY0014"; ErrorMetadata="LINE:5:COLUMN:11:" :)
let $data := delta-file("../../../queries/sample_updating_delta")
return (
    copy json $je := {"a" : 1}
    modify delete json $data.bool
    return $je
)
(: Attempt to modify mutable delta file value inside transform without copy :)
