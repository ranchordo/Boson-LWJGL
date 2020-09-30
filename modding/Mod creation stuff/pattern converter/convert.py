#This is a little conversion script that converts patterns from the original
#BosonX pattern format (lua arrays) to the format that Boson-LWJGL accepts.
#Example of a BosonX pattern:

#{name = "piece1", [[
#|  |  |P1|  |  |  |  |  |  |  |
#|  |  |P1|  |  |  |  |  |  |  |
#]]},
#
#{name = "piece2", [[
#|  |P1|  |  |  |P1|  |  |  |  |
#|  |P1|  |  |  |P1|  |  |  |  |
#|  |P1|  |  |  |P1|  |  |  |  |
#|  |P1|  |  |  |  |  |  |  |  |
#]]}

#Example of a Boson-LWJGL pattern:

#|  |  |P1|  |  |  |  |  |  |  |
#|  |  |P1|  |  |  |  |  |  |  |
#;
#|  |P1|  |  |  |P1|  |  |  |  |
#|  |P1|  |  |  |P1|  |  |  |  |
#|  |P1|  |  |  |P1|  |  |  |  |
#|  |P1|  |  |  |  |  |  |  |  |
#;

#In order to run this script, put .lua files in the same directory as this
#script and run it. It will create corresponding pattern files in the
#Boson-LWJGL format.

i=101
f=open("patterns_stage"+str(i)+".lua","r")
fo=open("./patterns_"+str(i)+".txt","w+")
a=f.read()
b=a.split("[[")
c=[t.split("]]")[0] for t in b][1:]
for o in c:
    fo.write(o[1:-1])
    fo.write("\n;\n")
f.close()
fo.close()
