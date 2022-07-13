echo -------------------------------------------------------------------
echo 리소스 빌드
echo -------------------------------------------------------------------
call resourcebuild wt.epm.EPMApplicationTypeRB
call resourcebuild wt.epm.EPMAuthoringAppTypeRB
call resourcebuild wt.lifecycle.StateRB
call resourcebuild wt.part.QuantityUnitRB
call resourcebuild wt.project.RoleRB

call resourcebuild e3ps.common.code.*
call resourcebuild e3ps.common.impl.*
call resourcebuild e3ps.edm.*
call resourcebuild e3ps.groupware.board.*
call resourcebuild e3ps.groupware.company.*
call resourcebuild e3ps.project.*
call resourcebuild e3ps.project.outputtype.*
call ant -f D:/ptc/Windchill/codebase/MakeJar.xml
