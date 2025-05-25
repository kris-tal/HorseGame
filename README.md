# Końtra

Celem projektu jest stworzenie gry wyścigowej `Końtra` opartej na rywalizacji 1v1. W grze
znajdzie się zarówno część single-player, jak i multiplayer.

## Zarys fabularny i założenia gry
W celu ucieczki od rzeczywistości postanowiłeś wyjechać
ma wieś. Od dawna marzyłeś o karierze wyścigów konnych i teraz jest Twoja okazja! Oswajaj konie, trenuj je i ścigaj się z innymi graczami w grze `Końtra`


W rozgrywce możemy wyróżnić kilka faz, w których gracz może "progressować"
- Stadnina
> Każdy gracz ma swoją stadninę, w której przechowuje konie i ich prowiant. Właśnie tutaj jest miejsce
> dla jego koni, gdzie o nie dba, karmi i zapewnia im warunki do życia.

## Wykorzystane technologie

Architektura projektu przedstawia sie następująco:
- Za podstawę projektu odpowiada `Java` z frameworkiem `LibGDX`, 
w którym napisaliśmy clienta
- Gra odbywa się lokalnie, synchronizując dane z serwerem zasobów
- Serwer oparty jest na `Flask`, który zarządza REST API z którym komunikuje się gracz
- Dane przechowywane są w bazie danych `PostgreSQL`
- W przyszłości tryb multiplayer, czyli wyścigi, będą oparte o komunikację `Websocket` z serwerem