import sys
import json
import re
import commands
import os
import shutil
import time

def unpack_apk(path, output):
	return commands.getstatusoutput('apktool d -f -s {0} -o {1}'.format(path, output))

def load_channels():
	return json.load(open('channels.json', 'r'))

def load_key_file():
	return json.load(open('signed_key.json', 'r'))

def clean_splash_icon(input_dir):
	res_base = '%s/res/' % input_dir
	dir_list = os.listdir(res_base)
	for dir in dir_list:
		if dir.find('drawable') != -1:
			splash = os.path.join(res_base, dir, 'ic_splash.png')
			if os.path.isfile(splash):
				os.remove(splash)
				sys.stdout.write('splash %s is removed!' % splash)
		
	return 0

def copy_splash_icon(channel, input_dir):
	sys.stdout.write('copy_splash_icon for channel %s' % channel)
	res_base = '%s/res/' % input_dir
	channel_base = '{dir}/channel_splash/{channel}'.format(dir=os.getcwd(), channel=channel)
	if os.path.isdir(channel_base) == False:
		sys.stdout.write('%s not exists' % channel_base)
		return 0
	if os.path.isdir(res_base) == False:
		sys.stdout.write('%s not exists' % res_base)
		return 0

	list = os.listdir(res_base)
	for dir in list:
		if dir.find('drawable') != -1:
			channel_list = os.listdir(channel_base)
			for ch_dir in channel_list:
				if dir.find(ch_dir) != -1:
					res_dir = os.path.join(res_base, dir)
					ic_file = os.path.join(channel_base, ch_dir, 'ic_splash.png')
					if os.path.isdir(res_dir) and os.path.isfile(ic_file):
						shutil.copy(ic_file, os.path.join(res_dir, 'ic_splash.png'))
						sys.stdout.write('copy %s finish' % dir)
	sys.stdout.write('copy channel %s splash success' % channel)
	return 0

# 0 is __file,1 is path of apk, 2 is name of project
if len(sys.argv) < 3:
	sys.stderr.write('wrong input args. args is {0}'.format(sys.argv))
	sys.exit()
	
#apk decode
start = time.time()
output_dir = '%s/build/outputs/apk/channel_apks' % os.getcwd()
apk_dir_name = sys.argv[1][sys.argv[1].rfind('/'):sys.argv[1].rfind('.apk')]
input_dir = '%s/build/outputs/apk/%s' % (os.getcwd(), apk_dir_name)
unzip_result = unpack_apk(sys.argv[1], input_dir)
if unzip_result[0] != 0:
	sys.stderr.write('unzip failed. message is {0}'.format(unzip_result[1]))
	sys.exit()

sys.stdout.write('unpack apk success!')

# read channel json
channel_cfg = load_channels()
channels = channel_cfg['channels']
splash_icon = channel_cfg['splash_icon']
project_name = sys.argv[2]

sys.stdout.write('input base dir is {0}'.format(input_dir))

sys.stdout.write('output base dir is {0}'.format(output_dir))

if os.path.exists(output_dir):
	shutil.rmtree(output_dir)

os.mkdir(output_dir)

# read manifest content
manifest_path = ''.join([input_dir, '/AndroidManifest.xml'])
manifest_file = open(manifest_path, 'r')
try:
	manifest_content = manifest_file.readlines()
finally:
	manifest_file.close()

# pattern = r'(\s*<meta-data\s+android:name="(JPUSH|UMENG)_CHANNEL"\s+android:value=\s*")(.+)("\s*/>)'
pattern = r'\s*android:value=\s*"{channel}"\s*/>"'
# load key file
store = load_key_file()

pack_info = {}

for channel in channels:
	manifest_file = open(manifest_path, 'w+')
	# replacement = r"\g{channel}\g".format(channel=channel['channel'])		
	try:
		for line in manifest_content:
			# if re.match(pattern, line, re.I):
			if line.find('{channel}') > -1:
				sys.stdout.write('reg matches, new is %s' % line.format(channel=channel['channel']))
				# manifest_file.write(re.sub(pattern, replacement, line))
				manifest_file.write(line.format(channel=channel['channel']))
			else:	
				manifest_file.write(line)
	finally:
		manifest_file.close()	

	output_apk = ''.join([output_dir, '/', channel['name'], '.apk'])
	sys.stdout.write('output apk is {0}'.format(output_apk))

	clean_splash_icon(input_dir)
	if len(splash_icon) > 0 and channel['channel'] in splash_icon:
		copy_splash_icon(channel['channel'], input_dir)

	pack_result = commands.getstatusoutput('apktool b -f {folder} -o {apk}'.format(folder=input_dir, apk=output_apk))	
	
	if pack_result[0] != 0:
		sys.stdout.write("pack failed on {channel}, reason is: {why}".format(channel=channel['name'], why=pack_result[1]))
		sys.exit()
	
	signed_apk = ''.join([output_dir, '/', project_name, '-', channel['name'], '-release.apk'])
	sys.stdout.write("pack success on {0}".format(channel['name']))
	sign_result = commands.getstatusoutput('jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore {storeFile} -storepass {storePwd} -keypass {keyPass} -signedjar {signedjar} {unsignedjar} {alias}'.format(storeFile=store['storeFile'], storePwd=store['storePwd'], keyPass=store['keyPwd'], signedjar=signed_apk,unsignedjar=output_apk,alias=store['keyAlias']))
	
	if sign_result[0] != 0:
		sys.stderr.write("signed jar failed on {channel}, reason is: {why}".format(channel=channel['name'], why=sign_result[1]))
		sys.exit()

	sys.stdout.write("signed jar success: {0}".format(signed_apk))

	# delete unsigned apk
	os.remove(output_apk)	

shutil.rmtree(input_dir)
cost_minutes = (time.time() - start) / 60
left_sec = (time.time() - start) % 60
sys.stdout.write('finish:cost %d minutes %d seconds' % (cost_minutes, left_sec))
