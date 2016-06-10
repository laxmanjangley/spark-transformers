import sys
if(len(sys.argv) != 2):
	print "Usage: python gen.py <input_file>"
file  = open(sys.argv[1])
flag = 0
vararray = []
classname = ""
package = ""
while True:
	line = file.readline()
	if (line[:7] == "package"):
		package = line[8:-1]
	if not line:
		break
	if(line[0] == 'c'):
		if(flag == 0):
			classname = line.split(' ')[1].split('(')[0]
		flag += 1
	if (flag == 1):
		if(line[:5] == "	val "):
			temp = line.split(' ')
			vararray.append((temp[1], temp[4].split('(')[0][6:-1]))

package =  package + '.' + classname
mit = open("modelinfo_template.java", 'r')
mic = open(classname+"modelinfo.java", 'w')
while True:
	flag = False
	line = mit.readline()
	if not line:
		break
	i = line.find("<XXX>")
	if(i != -1):
		flag = True
		nline = line[:i] + classname + line[(i+5):]
		mic.write(nline)
	i = line.find("private ")
	if(i != -1):
		flag = True
		for (var, ty) in vararray:
			if (ty[:3] == "Seq"):
				ty = "Array"+ty[3:]
			j = ty.find("=>")
			if(j != -1):
				s = ty.split("=>")
				ty = "Function1<"+s[0]+','+s[1]+">"
				
			nline = "\tprivate "+ty+" "+var+";\n"
			mic.write(nline)
	if not flag:
		mic.write(line)
ait = open("adapter_template.java", 'r')
aic = open(classname + "Adapter.java", 'w')
while True:
	flag = True
	line = ait.readline()
	while flag:
		i = line.find("<XXX>")
		if(i != -1):
			print "laxman {}".format(i)
			flag = True
			line = line[:i] + classname + line[(i+5):]
		else:
			flag = False
			flag1 = True
			i = line.find("<Field1>")
			if(i != -1):
				flag1 = False
				for (var, ty) in vararray:
					var = var[0].upper()+var[1:]
					tline = line[:i]+var+line[(i+8):]
					tline  = tline.replace("<Field1>", var)
					aic.write(tline)
			if flag1:
				aic.write(line)
	if not line:
		break

