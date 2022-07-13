-- 디버깅 Code 사용안함으로 변경
update numbercode
   set disabled = 1
 where name = '디버깅'
   and codetype = 'MOLDTASKTYPE'
;

** Project Iteration Setting ***

-- Review Project PJT Iteration Setting
update reviewproject
   set pjtiteration = 0
 where pjtiteration is null
;

-- Product Project PJT Iteration Setting
update productproject
   set pjtiteration = 0
 where pjtiteration is null
;

-- Mold Project PJT Iteration Setting
update moldproject
   set pjtiteration = 0
 where pjtiteration is null
;

commit;