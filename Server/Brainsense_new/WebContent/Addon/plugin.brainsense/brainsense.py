import sys
import urllib
import urlparse
import xbmcgui
import xbmcplugin
import os
import os.path

base_url = sys.argv[0]
addon_handle = int(sys.argv[1])
args = urlparse.parse_qs(sys.argv[2][1:])
IMAGE_FILENAME = "plugin_image.txt"
VIDEO_FILENAME = "plugin_video.txt"
AUDIO_FILENAME = "plugin_audio.txt"
TYPE_VIDEO = "video"
TYPE_AUDIO = "audio"
TYPE_IMAGE = "image"

xbmcplugin.setContent(addon_handle, 'movies')

def build_url(query):
    return base_url + '?' + urllib.urlencode(query)

def init():
    url = build_url({'mode': TYPE_VIDEO, 'foldername': 'movie'})
    li = xbmcgui.ListItem('movies', iconImage='DefaultFolder.png')
    xbmcplugin.addDirectoryItem(handle=addon_handle, url=url, listitem=li, isFolder=True)

    url = build_url({'mode': TYPE_AUDIO, 'foldername': 'audio'})
    li = xbmcgui.ListItem('audios', iconImage='DefaultFolder.png')
    xbmcplugin.addDirectoryItem(handle=addon_handle, url=url, listitem=li, isFolder=True)

    url = build_url({'mode': TYPE_IMAGE, 'foldername': 'image'})
    li = xbmcgui.ListItem('images', iconImage='DefaultFolder.png')
    xbmcplugin.addDirectoryItem(handle=addon_handle, url=url, listitem=li, isFolder=True)
    xbmcplugin.endOfDirectory(addon_handle)

def getpackages():
    path = os.path.join(os.getcwd(), "resources", "content")
    packagemap = {}
    for parent,dirnames,filenames in os.walk(path):
        for dirname in  dirnames:
            packagemap[dirname] = os.path.join(parent, dirname)
        break
    return packagemap

def showpackages(type):
    packages = getpackages();
    for package in packages.keys():
        url = build_url({'mode': package, 'type': type, 'foldername': package})
        li = xbmcgui.ListItem(package, iconImage='DefaultFolder.png')
        xbmcplugin.addDirectoryItem(handle=addon_handle, url=url, listitem=li, isFolder=True)
    else:
        xbmcplugin.endOfDirectory(addon_handle)

def geturls(packagename, type):
    if type == TYPE_IMAGE:
        filename = IMAGE_FILENAME
    elif type == TYPE_AUDIO:
        filename = AUDIO_FILENAME
    elif type == TYPE_VIDEO:
        filename = VIDEO_FILENAME
    urls = {}
    packages = getpackages()
    file = open(os.path.join(packages[packagename], filename))
    lines = file.readlines()
    index = 0
    for i in range(1, len(lines) + 1):
        if(lines[i - 1] == "\n"):
            continue
        index = index + 1
        if(index % 2 == 0):
            urls[lines[i-2].replace("\r\n", "")] = lines[i-1].replace("\r\n", "")
    file.close()
    return urls

def showvideos(packagename, type):
    urls = geturls(packagename, type)
    if type == TYPE_AUDIO:
        iconname = 'DefaultAudio.png'
    if type == TYPE_VIDEO:
        iconname = 'DefaultVideo.png'
    if type == TYPE_IMAGE:
        iconname = 'DefaultImage.png'
    for key in urls.keys():
        foldername = key
        url = urls[key]
        li = xbmcgui.ListItem(foldername, iconImage=iconname)
        xbmcplugin.addDirectoryItem(handle=addon_handle, url=url, listitem=li)
    xbmcplugin.endOfDirectory(addon_handle)

def show(mode, type):
    global CURRENT_TYPE
    if mode is None:
        init()
    elif mode[0] == TYPE_VIDEO:
        showpackages(mode[0])
    elif mode[0] == TYPE_AUDIO:
        showpackages(mode[0])
    elif mode[0] == TYPE_IMAGE:
        showpackages(mode[0])
    else:
        showvideos(mode[0], type[0])

mode = args.get('mode', None)
type = args.get('type', None)
show(mode, type)

