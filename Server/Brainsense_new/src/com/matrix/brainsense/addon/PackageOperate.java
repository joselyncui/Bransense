package com.matrix.brainsense.addon;

public class PackageOperate {
	
	public boolean myExecute() {
		return true;
	}
	
	public boolean generate(AddonOperate addonOperate) {
		if(addonOperate.copyAddon() && 
				addonOperate.setIcon() &&
				addonOperate.copyPackage() &&
				addonOperate.unzipPackage() &&
				addonOperate.changeAddonName() &&
				addonOperate.zipAddon() &&
				myExecute()
				) {
			return true;
		}
		return false;
	}

}
