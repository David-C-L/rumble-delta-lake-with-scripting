(:JIQS: ShouldRun; Output="" :)
try { 1 * (2, 3); } catch XPTY0004 { "ok"; }
-70000*1;
1*70000;
1*-70000;
try { yearMonthDuration("P1Y2M") * "foo"; } catch XPTY0004 { "ok"; }
try { dayTimeDuration("P1D") * "foo"; } catch XPTY0004 { "ok"; }
try { 2 div yearMonthDuration("P1Y2M"); } catch XPTY0004 { "ok"; }
try { 3 div dayTimeDuration("P1D"); } catch XPTY0004 { "ok"; }
try { 2e0 div 0e0; } catch FOAR0001 { "ok"; }
try { 2e0 idiv 0e0;} catch FOAR0001 { "ok"; }
try { 2e0 mod 0e0; } catch FOAR0001 { "ok"; }
try { 2.1 div 0.0; } catch FOAR0001 { "ok"; }
try { 2.1 idiv 0.0; } catch FOAR0001 { "ok"; }
try { 2.1 mod 0.0; } catch FOAR0001 { "ok"; }
try { 999999999999999999 div 0; } catch FOAR0001 { "ok"; }
try { 999999999999999999 idiv 0 ;} catch FOAR0001 { "ok"; }
try { 999999999999999999 mod 0; } catch FOAR0001 { "ok"; }
try { 2 div 0; } catch FOAR0001 { "ok"; }
try { 2 idiv 0; } catch FOAR0001 { "ok"; }
try { 2 mod 0; } catch FOAR0001 { "ok";}
try { yearMonthDuration("P1Y2M") idiv 1; } catch XPTY0004 { "ok"; }
try { dayTimeDuration("P1D") idiv 1; } catch XPTY0004 { "ok"; }
try { yearMonthDuration("P1Y2M") idiv yearMonthDuration("P1Y2M"); } catch XPTY0004 { "ok"; }
try { dayTimeDuration("P1D") idiv dayTimeDuration("P1D"); } catch XPTY0004 { "ok"; }
try { 2 idiv "P1D"; } catch XPTY0004 { "ok"; }
try { "P1D" idiv 2; } catch XPTY0004 { "ok"; }